package my.project.onlineAuctionBackend.services

import my.project.onlineAuctionBackend.models.Bid
import my.project.onlineAuctionBackend.models.Product
import my.project.onlineAuctionBackend.models.User
import my.project.onlineAuctionBackend.repositories.BidRepository
import my.project.onlineAuctionBackend.repositories.ProductRepository
import my.project.onlineAuctionBackend.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class BidService(
    private val bidRepository: BidRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) {

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

    fun placeBid(productId: Long, bidderId: Long, bidAmount: Double): Bid {
        val product = productRepository.findById(productId)
            .orElseThrow { RuntimeException("Product not found") }

        val bidder = userRepository.findById(bidderId)
            .orElseThrow { RuntimeException("User not found") }

        val highestBid = bidRepository.findTopByProductOrderByAmountDesc(product)
        if (highestBid != null && bidAmount <= highestBid.bidAmount) {
            throw RuntimeException("Bid amount must be higher than the current highest bid")
        }

        val bid = Bid(product = product, bidder = bidder, bidAmount = bidAmount)
        return bidRepository.save(bid)
    }

}
