package my.project.onlineAuctionBackend.controllers

import my.project.onlineAuctionBackend.models.User
import my.project.onlineAuctionBackend.services.UserService
import my.project.onlineAuctionBackend.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/users")
class UserController(private val userService: UserService){

    @GetMapping
    fun getAllUser(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return if (users.isNotEmpty()) {
            ResponseEntity.ok(users)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<Any> {
        val user: Optional<User> = userService.getUserById(id)
        return if (user.isPresent) {
            ResponseEntity.ok(user.get())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "User not found"))
        }
    }

    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User>{
        val createUser = userService.createUser(user)
        return ResponseEntity(createUser, HttpStatus.CREATED)
    }


    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val user = userService.getUserById(id)
        return if (user.isPresent) {
            userService.deleteUser(id)
            ResponseEntity.ok(mapOf("message" to "User deleted successfully"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "User not found"))
        }
    }
}
