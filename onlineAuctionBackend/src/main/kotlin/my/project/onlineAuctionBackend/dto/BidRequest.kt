package my.project.onlineAuctionBackend.dto

data class BidRequest(
    val productId: Long,
    val bidderId: Long,
    val bidAmount: Double
)