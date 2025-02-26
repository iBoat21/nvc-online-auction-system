package my.project.onlineAuctionBackend.repositories

import my.project.onlineAuctionBackend.models.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository: JpaRepository<Category, Long> {
    fun findByCategoryNameIgnoreCase(categoryName: String): Optional<Category>
}