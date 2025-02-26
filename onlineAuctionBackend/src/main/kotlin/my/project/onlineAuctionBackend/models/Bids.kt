package my.project.onlineAuctionBackend.models

import java.util.Date
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "bids")
data class Bid(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id", nullable = false) // ผู้ประมูลต้องมีเสมอ
    val bidder: User,

    @Column(name = "bid_amount", nullable = false)
    val bidAmount: Double,

    @Column(name = "bid_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val bidTime: Date = Date(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false) // ต้องมีสินค้า
    val product: Product,


)
