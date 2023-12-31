package fr.dev.sydher.financicraft.rc

import fr.dev.sydher.financicraft.bean.ApiResponse
import fr.dev.sydher.financicraft.bean.entity.Account
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

    @Operation(summary = "Get account by id", tags = [AppConst.SWAGGER_TAG_ACCOUNT])
    @GetMapping("/{id}")
    fun find(@PathVariable id: Long): ResponseEntity<ApiResponse<Account>> {
        return ResponseEntity(getResponse(accountDS.find(id)), HttpStatus.OK)
    }

    @Operation(summary = "Get all accounts in a category", tags = [AppConst.SWAGGER_TAG_ACCOUNT])
    @GetMapping("/category/{category}")
    fun findAll(@PathVariable category: String): ResponseEntity<ApiResponse<List<Account>>> {
        return ResponseEntity(getResponse(accountDS.findAll(category)), HttpStatus.OK)
    }

    @Operation(summary = "Get all accounts", tags = [AppConst.SWAGGER_TAG_ACCOUNT])
    @GetMapping("/")
    fun getAll(@PathVariable id: Long): ResponseEntity<ApiResponse<List<Account>>> {
        return ResponseEntity(getResponse(accountDS.getAll()), HttpStatus.OK)
    }

    @Operation(summary = "Create account", tags = [AppConst.SWAGGER_TAG_ACCOUNT])
    @PostMapping("/")
    fun create(@RequestBody account: Account): ResponseEntity<ApiResponse<Account>> {
        return ResponseEntity(getResponse(accountDS.save(account)), HttpStatus.CREATED)
    }

    @Operation(summary = "Update account", tags = [AppConst.SWAGGER_TAG_ACCOUNT])
    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestBody account: Account): ResponseEntity<ApiResponse<Account>> {
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