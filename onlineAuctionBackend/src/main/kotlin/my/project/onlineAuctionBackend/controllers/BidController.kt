package my.project.onlineAuctionBackend.controllers

import my.project.onlineAuctionBackend.models.Bid
import my.project.onlineAuctionBackend.services.BidService
import my.project.onlineAuctionBackend.dto.BidRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/bids")
class BidController(private val bidService: BidService) {

    // ดึงรายการประมูลทั้งหมด
    @GetMapping
    fun getAllBids(): ResponseEntity<List<Bid>> {
        val bids = bidService.getAllBids()
        return if (bids.isNotEmpty()) {
            ResponseEntity.ok(bids)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    // ดึงข้อมูลการประมูลโดยใช้ ID
    @GetMapping("/{id}")
    fun getBidById(@PathVariable id: Long): ResponseEntity<Any> {
        val bid = bidService.getBidById(id)
        return if (bid.isPresent) {
            ResponseEntity.ok(bid.get())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "Bid not found"))
        }
    }

    // ดึงการประมูลของสินค้าแต่ละชิ้น
    @GetMapping("/product/{productId}")
    fun getBidsByProduct(@PathVariable productId: Long): ResponseEntity<List<Bid>> {
        val bids = bidService.getBidsByProduct(productId)
        return ResponseEntity.ok(bids)
    }

    // ดึงการประมูลของผู้ใช้แต่ละคน
    @GetMapping("/bidder/{bidderId}")
    fun getBidsByBidder(@PathVariable bidderId: Long): ResponseEntity<List<Bid>> {
        val bids = bidService.getBidsByBidder(bidderId)
        return ResponseEntity.ok(bids)
    }

    // เพิ่มการประมูลใหม่
    @PostMapping
    fun createBid(@RequestBody request: BidRequest): ResponseEntity<Any> {
        return try {
            val bid = bidService.placeBid(request.productId, request.bidderId, request.bidAmount) // ✅ ใช้ placeBid() แทน
            ResponseEntity.status(HttpStatus.CREATED).body(bid)
        } catch (e: RuntimeException) {
            ResponseEntity.badRequest().body(mapOf("message" to e.message))
        }
    }

    @PostMapping("/place")
    fun placeBid(@RequestBody request: BidRequest): ResponseEntity<Any> {
        return try {
            val bid = bidService.placeBid(request.productId, request.bidderId, request.bidAmount) // ✅ ใช้ bidderId & bidAmount ให้ตรงกัน
            ResponseEntity.ok(mapOf("message" to "Bid placed successfully", "bid" to bid))
        } catch (e: RuntimeException) {
            ResponseEntity.badRequest().body(mapOf("message" to e.message))
        }
    }

    // ลบการประมูล
    @DeleteMapping("/{id}")
    fun deleteBid(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        return try {
            bidService.deleteBid(id)
            ResponseEntity.ok(mapOf("message" to "Bid deleted successfully"))
        } catch (e: RuntimeException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to e.message!!))
        }
    }
}
