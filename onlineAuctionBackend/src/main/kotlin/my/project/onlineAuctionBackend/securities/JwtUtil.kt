package my.project.onlineAuctionBackend.securities

import io.jsonwebtoken.*
import io.jsonwebtoken.io.*
import io.jsonwebtoken.security.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {
    private val secretKey = "mySecretKeymySecretKeymySecretKeymySecretKey" // ควรใช้ Base64 Encoded String
    private val expirationMs = 86400000 // 24 ชั่วโมง

    private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))

    fun generateToken(email: String): String {
        return Jwts.builder()
            .subject(email)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
            true
        } catch (e: JwtException) {
            false
        }
    }

    fun getEmailFromToken(token: String): String {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload.subject
    }
}
