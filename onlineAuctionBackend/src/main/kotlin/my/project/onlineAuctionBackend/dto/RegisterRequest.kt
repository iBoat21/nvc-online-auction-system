package my.project.onlineAuctionBackend.dto

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)
