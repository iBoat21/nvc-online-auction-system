package my.project.onlineAuctionBackend.controllers

import my.project.onlineAuctionBackend.models.Category
import my.project.onlineAuctionBackend.services.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAllCategories(): ResponseEntity<List<Category>> {
        val categories = categoryService.getAllCategories()
        return if (categories.isNotEmpty()) {
            ResponseEntity.ok(categories)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<Any> {
        val category = categoryService.getCategoryById(id)
        return if (category.isPresent) {
            ResponseEntity.ok(category.get())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "Category not found"))
        }
    }

    @PostMapping
    fun createCategory(@RequestBody category: Category): ResponseEntity<Category> {
        val createdCategory = categoryService.createCategory(category)
        return ResponseEntity(createdCategory, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: Long, @RequestBody category: Category): ResponseEntity<Any> {
        return try {
            val updatedCategory = categoryService.updateCategory(id, category)
            ResponseEntity.ok(updatedCategory)
        } catch (e: RuntimeException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to (e.message ?: "Category not found")))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        return try {
            categoryService.deleteCategory(id)
            ResponseEntity.ok(mapOf("message" to "Category deleted successfully"))
        } catch (e: RuntimeException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to (e.message ?: "Category not found")))
        }
    }
}
