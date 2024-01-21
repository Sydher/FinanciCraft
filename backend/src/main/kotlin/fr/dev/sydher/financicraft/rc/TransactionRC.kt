package fr.dev.sydher.financicraft.rc

import fr.dev.sydher.financicraft.bean.ApiResponse
import fr.dev.sydher.financicraft.bean.dto.TransactionDTO
import fr.dev.sydher.financicraft.bean.exception.TransactionNotFoundException
import fr.dev.sydher.financicraft.ds.TransactionDS
import fr.dev.sydher.financicraft.utils.AppConst
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transactions")
class TransactionRC @Autowired constructor(private val transactionDS: TransactionDS) : AbstractAppRC() {

    @Operation(summary = "Get all transactions", tags = [AppConst.SWAGGER_TAG_TRANSACTION])
    @GetMapping("/")
    fun getAll(): ResponseEntity<ApiResponse<List<TransactionDTO>>> {
        return ResponseEntity(getResponse(transactionDS.getAll()), HttpStatus.OK)
    }

    @Operation(summary = "Get transaction by id", tags = [AppConst.SWAGGER_TAG_TRANSACTION])
    @GetMapping("/{id}")
    fun find(@PathVariable id: Long): ResponseEntity<ApiResponse<TransactionDTO>> {
        return try {
            ResponseEntity(getResponse(transactionDS.find(id)), HttpStatus.OK)
        } catch (e: TransactionNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @Operation(summary = "Get all transactions in an account", tags = [AppConst.SWAGGER_TAG_TRANSACTION])
    @GetMapping("/account/{accountId}")
    fun findAllByAccount(@PathVariable accountId: Long): ResponseEntity<ApiResponse<List<TransactionDTO>?>> {
        return ResponseEntity(getResponse(transactionDS.findAllByAccount(accountId)), HttpStatus.OK)
    }

    @Operation(summary = "Create transaction", tags = [AppConst.SWAGGER_TAG_TRANSACTION])
    @PostMapping("/")
    fun create(@RequestBody transaction: TransactionDTO): ResponseEntity<ApiResponse<TransactionDTO>> {
        return ResponseEntity(getResponse(transactionDS.save(transaction)), HttpStatus.CREATED)
    }

    @Operation(summary = "Update transaction", tags = [AppConst.SWAGGER_TAG_TRANSACTION])
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody transaction: TransactionDTO
    ): ResponseEntity<ApiResponse<TransactionDTO>> {
        return ResponseEntity(getResponse(transactionDS.save(transaction)), HttpStatus.OK)
    }

    @Operation(summary = "Delete transaction", tags = [AppConst.SWAGGER_TAG_TRANSACTION])
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse<Boolean>> {
        try {
            transactionDS.delete(id)
            return ResponseEntity(getResponse(true), HttpStatus.OK)
        } catch (e: TransactionNotFoundException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}
