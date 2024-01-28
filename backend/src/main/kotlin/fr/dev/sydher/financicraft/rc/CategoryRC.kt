package fr.dev.sydher.financicraft.rc

import fr.dev.sydher.financicraft.bean.ApiResponse
import fr.dev.sydher.financicraft.bean.dto.CategoryDTO
import fr.dev.sydher.financicraft.bean.exception.CategoryNotFoundException
import fr.dev.sydher.financicraft.ds.CategoryDS
import fr.dev.sydher.financicraft.utils.AppConst
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryRC @Autowired constructor(private val categoryDS: CategoryDS) : AbstractAppRC() {

    @Operation(summary = "Get all categories", tags = [AppConst.SWAGGER_TAG_CATEGORY])
    @GetMapping("/")
    fun getAllCategories(): ResponseEntity<ApiResponse<List<CategoryDTO>>> {
        return ResponseEntity(getResponse(categoryDS.getAll()), HttpStatus.OK)
    }

    @Operation(summary = "Get category by id", tags = [AppConst.SWAGGER_TAG_CATEGORY])
    @GetMapping("/{id}")
    fun findCategory(@PathVariable id: Long): ResponseEntity<ApiResponse<CategoryDTO>> {
        return try {
            ResponseEntity(getResponse(categoryDS.find(id)), HttpStatus.OK)
        } catch (e: CategoryNotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @Operation(summary = "Create category", tags = [AppConst.SWAGGER_TAG_CATEGORY])
    @PostMapping("/")
    fun createCategory(@RequestBody category: CategoryDTO): ResponseEntity<ApiResponse<CategoryDTO>> {
        return ResponseEntity(getResponse(categoryDS.save(category)), HttpStatus.CREATED)
    }

    @Operation(summary = "Update category", tags = [AppConst.SWAGGER_TAG_CATEGORY])
    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: String, @RequestBody category: CategoryDTO): ResponseEntity<ApiResponse<CategoryDTO>> {
        return ResponseEntity(getResponse(categoryDS.save(category)), HttpStatus.OK)
    }

    @Operation(summary = "Delete category", tags = [AppConst.SWAGGER_TAG_CATEGORY])
    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<ApiResponse<Boolean>> {
        try {
            categoryDS.delete(id)
            return ResponseEntity(getResponse(true), HttpStatus.OK)
        } catch (e: CategoryNotFoundException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}
