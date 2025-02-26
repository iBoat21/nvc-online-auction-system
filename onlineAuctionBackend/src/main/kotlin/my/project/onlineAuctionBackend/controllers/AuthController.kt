package my.project.onlineAuctionBackend.controllers

import my.project.onlineAuctionBackend.models.User
import my.project.onlineAuctionBackend.services.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseEntity<Any> {
        return try {
            val message = authService.register(user)
            ResponseEntity.ok(mapOf("message" to message))
        } catch (e: RuntimeException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("message" to e.message))
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: Map<String, String>): ResponseEntity<Any> {
        val username = loginRequest["username"] ?: return ResponseEntity.badRequest().body(mapOf("message" to "Username is required"))
        val password = loginRequest["password"] ?: return ResponseEntity.badRequest().body(mapOf("message" to "Password is required"))

        return try {
            val token = authService.login(username, password)
            ResponseEntity.ok(mapOf("token" to token))
        } catch (e: RuntimeException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("message" to e.message))
        }
    }
}
