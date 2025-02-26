package my.project.onlineAuctionBackend.models

import jakarta.persistence.*

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "category_name", nullable = false, unique = true)
    val categoryName: String
)