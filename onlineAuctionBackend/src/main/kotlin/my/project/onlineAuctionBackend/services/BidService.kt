package my.project.onlineAuctionBackend.services

import my.project.onlineAuctionBackend.models.Bid
import my.project.onlineAuctionBackend.repositories.BidRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BidService(private val bidRepository: BidRepository) {

    // ดึงรายการประมูลทั้งหมด
    fun getAllBids(): List<Bid> = bidRepository.findAll()

    // ดึงข้อมูลการประมูลโดยใช้ ID
    fun getBidById(id: Long): Optional<Bid> = bidRepository.findById(id)

    // ดึงรายการประมูลของสินค้าแต่ละชิ้น
    fun getBidsByProduct(productId: Long): List<Bid> = bidRepository.findByProductId(productId)

    // ดึงรายการประมูลของผู้ใช้แต่ละคน
    fun getBidsByBidder(bidderId: Long): List<Bid> = bidRepository.findByBidderId(bidderId)

    // เพิ่มการประมูลใหม่
    fun createBid(bid: Bid): Bid = bidRepository.save(bid)

    // ลบการประมูล
    fun deleteBid(id: Long) {
        if (bidRepository.existsById(id)) {
            bidRepository.deleteById(id)
        } else {
            throw RuntimeException("Bid id: $id not found")
        }
    }
}
