package my.project.onlineAuctionBackend.repositories

import my.project.onlineAuctionBackend.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username:String): User?
    fun existsByUsername(username: String?): Boolean
    fun findByEmail(email:String): User?
}