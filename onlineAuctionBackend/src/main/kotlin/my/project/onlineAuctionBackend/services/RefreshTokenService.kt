package my.project.onlineAuctionBackend.services

import my.project.onlineAuctionBackend.repositories.UserRepository

@Service
class RefreshTokenService(private val jwtService: JwtService, private val userRepository: UserRepository) {

    fun refreshToken(refreshToken: String): String {
        val username = jwtService.extractUsername(refreshToken)
        val user = userRepository.findByUsername(username) ?: throw RuntimeException("User not found")

        if (!jwtService.validateToken(refreshToken, user)) {
            throw RuntimeException("Invalid Refresh Token")
        }

        return jwtService.generateToken(user)
    }
}