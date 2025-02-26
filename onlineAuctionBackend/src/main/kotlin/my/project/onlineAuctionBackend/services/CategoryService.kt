package my.project.onlineAuctionBackend.services

import my.project.onlineAuctionBackend.models.Category
import my.project.onlineAuctionBackend.repositories.CategoryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService (private val categoryRepository: CategoryRepository){
    fun getAllCategories(): List<Category> = categoryRepository.findAll()
    fun getCategoryById(id: Long): Optional<Category> = categoryRepository.findById(id)
    fun createCategory(category: Category): Category = categoryRepository.save(category)
    fun updateCategory(id: Long, updateCategory: Category): Category {
        return if (categoryRepository.existsById(id)) {
            val existingCategory = updateCategory.copy(id = id)
            categoryRepository.save(existingCategory)
        } else {
            throw RuntimeException("Category id: $id not found")
        }
    }
    fun deleteCategory(id: Long) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id)
        } else {
            throw RuntimeException("Category id: $id not found")
        }
    }
}