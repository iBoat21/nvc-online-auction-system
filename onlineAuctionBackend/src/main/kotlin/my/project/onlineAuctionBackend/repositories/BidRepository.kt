package my.project.onlineAuctionBackend.repositories

import my.project.onlineAuctionBackend.models.Bid
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BidRepository : JpaRepository<Bid, Long> {
    fun findByProductId(productId: Long): List<Bid> // ดึงการประมูลตามสินค้า
    fun findByBidderId(bidderId: Long): List<Bid>   // ดึงการประมูลของผู้ใช้
}