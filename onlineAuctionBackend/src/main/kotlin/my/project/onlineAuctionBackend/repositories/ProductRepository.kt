package my.project.onlineAuctionBackend.repositories

import java.util.Date
import my.project.onlineAuctionBackend.models.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long> {
    fun findByCategoryId(categoryId: Long): List<Product>
    fun findBySellerId(sellerId: Long): List<Product>
    fun findByItemEndDateBefore(endDate: Date): List<Product>
    fun findByItemNameContainingIgnoreCase(itemName: String): List<Product>
}