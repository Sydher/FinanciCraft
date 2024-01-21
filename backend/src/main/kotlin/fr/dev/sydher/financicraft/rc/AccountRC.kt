package fr.dev.sydher.financicraft.rc

import fr.dev.sydher.financicraft.bean.ApiResponse
import fr.dev.sydher.financicraft.bean.dto.AccountDTO
import fr.dev.sydher.financicraft.bean.exception.AccountNotFoundException
import fr.dev.sydher.financicraft.ds.AccountDS
import fr.dev.sydher.financicraft.utils.AppConst
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/accounts")
class AccountRC @Autowired constructor(private val accountDS: AccountDS) : AbstractAppRC() {

    @Operation(summary = "Get all accounts", tags = [AppConst.SWAGGER_TAG_ACCOUNT])
    @GetMapping("/")
    fun getAll(): ResponseEntity<ApiResponse<List<AccountDTO>>> {
        return ResponseEntity(getResponse(accountDS.getAll()), HttpStatus.OK)
    }

    @Operation(summary = "Get account by id", tags = [AppConst.SWAGGER_TAG_ACCOUNT])
    @GetMapping("/{id}")
    fun find(@PathVariable id: Long): ResponseEntity<ApiResponse<AccountDTO>> {
        return try {
            ResponseEntity(getResponse(accountDS.find(id)), HttpStatus.OK)
        } catch (e: AccountNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @Operation(summary = "Get all accounts in a category", tags = [AppConst.SWAGGER_TAG_ACCOUNT])
    @GetMapping("/category/{categoryId}")
    fun findAll(@PathVariable categoryId: Long): ResponseEntity<ApiResponse<List<AccountDTO>?>> {
        return ResponseEntity(getResponse(accountDS.findAll(categoryId)), HttpStatus.OK)
    }

    @Operation(summary = "Create account", tags = [AppConst.SWAGGER_TAG_ACCOUNT])
    @PostMapping("/")
    fun create(@RequestBody account: AccountDTO): ResponseEntity<ApiResponse<AccountDTO>> {
        return ResponseEntity(getResponse(accountDS.save(account)), HttpStatus.CREATED)
    }

    @Operation(summary = "Update account", tags = [AppConst.SWAGGER_TAG_ACCOUNT])
    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody account: AccountDTO): ResponseEntity<ApiResponse<AccountDTO>> {
        return ResponseEntity(getResponse(accountDS.save(account)), HttpStatus.OK)
    }

    @Operation(summary = "Delete account", tags = [AppConst.SWAGGER_TAG_ACCOUNT])
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse<Boolean>> {
        try {
            accountDS.delete(id)
            return ResponseEntity(getResponse(true), HttpStatus.OK)
        } catch (e: AccountNotFoundException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}
