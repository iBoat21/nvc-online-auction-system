package my.project.onlineAuctionBackend.models

import java.util.Date
import jakarta.persistence.*

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "item_name", nullable = false)
    val itemName: String,

    @Column(name = "item_description", nullable = false)
    val itemDescription: String,

    @Column(name = "item_price", nullable = false)
    val itemPrice: Double = 0.0,

    @Column(name = "item_photo")
    val itemPhoto: String? = null,

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val createdAt: Date = Date(),

    @Column(name = "item_start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val itemStartDate: Date = Date(),

    @Column(name = "item_end_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    val itemEndDate: Date,

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = true)
    val seller: User? = null,

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    val bids: MutableList<Bid> = mutableListOf()
)
