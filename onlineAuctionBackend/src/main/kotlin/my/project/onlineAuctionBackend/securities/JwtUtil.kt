package my.project.onlineAuctionBackend.securities

import io.jsonwebtoken.*
import io.jsonwebtoken.io.*
import io.jsonwebtoken.security.*
import org.springframework.stereotype.Component
import java.util.*

    @Component
    class JwtUtil {
        private val secretKey = Base64.getEncoder().encodeToString("mySuperSecretKeyForJwtAuthentication".toByteArray())
        private val expirationMs = 86400000 // 24 ชั่วโมง

        private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))

        fun generateToken(username: String): String {
            return Jwts.builder()
                .subject(username)
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

        fun extractUsername(token: String): String {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).payload.subject
        }

        fun generateRefreshToken(username: String): String {
            return Jwts.builder()
                .subject(username)
                .issuedAt(Date())
                .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 วัน
                .signWith(key)
                .compact()
        }

        fun getEmailFromToken(token: String): String {
            return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload.subject
        }


    }