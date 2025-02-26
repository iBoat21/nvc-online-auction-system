package my.project.onlineAuctionBackend.services

import my.project.onlineAuctionBackend.models.Product
import my.project.onlineAuctionBackend.models.Category
import my.project.onlineAuctionBackend.repositories.CategoryRepository
import my.project.onlineAuctionBackend.repositories.ProductRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(private val productRepository: ProductRepository, private val categoryRepository: CategoryRepository) {

    // ดึงสินค้าทั้งหมด
    fun getAllProducts(): List<Product> = productRepository.findAll()

    // ดึงสินค้าตาม ID
    fun getProductById(id: Long): Optional<Product> = productRepository.findById(id)

    // ดึงสินค้าตามชื่อสินค้า (รองรับการค้นหาแบบพิมพ์เล็ก/ใหญ่ไม่สนใจ)
    fun getProductsByName(name: String): List<Product> = productRepository.findByItemNameContainingIgnoreCase(name)

    // ดึงสินค้าตามหมวดหมู่
    fun getProductsByCategory(categoryId: Long): List<Product> = productRepository.findByCategoryId(categoryId)

    // ดึงสินค้าตามผู้ขาย
    fun getProductsBySeller(sellerId: Long): List<Product> = productRepository.findBySellerId(sellerId)

    // ดึงสินค้าที่หมดเวลาประมูลแล้ว
    fun getExpiredProducts(): List<Product> = productRepository.findByItemEndDateBefore(Date())

    fun findCategoryById(categoryId: Long): Category? {
        return categoryRepository.findById(categoryId).orElse(null)
    }

    fun saveProduct(product: Product): Product {
        return productRepository.save(product)
    }

    // เพิ่มสินค้าใหม่
    fun createProduct(product: Product): Product = productRepository.save(product)

    // อัปเดตสินค้า
    fun updateProduct(id: Long, updateProduct: Product): Product {
        return if (productRepository.existsById(id)) {
            val existingProduct = updateProduct.copy(id = id)
            productRepository.save(existingProduct)
        } else {
            throw RuntimeException("Product id: $id not found")
        }
    }

    // ลบสินค้า
    fun deleteProduct(id: Long) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
        } else {
            throw RuntimeException("Product id: $id not found")
        }
    }
}
