package my.project.onlineAuctionBackend.services

import my.project.onlineAuctionBackend.models.User
import my.project.onlineAuctionBackend.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    // select * from user
    fun getAllUsers(): List<User> = userRepository.findAll()
    // select * from user where id = $id
    fun getUserById(id: Long): Optional<User> = userRepository.findById(id)
    // select * from user where username = $username
    fun getUserByUsername(username: String): User? = userRepository.findByUsername(username)
    // select * from user where email = $email
    fun getUserByEmail(email: String?): User? = userRepository.findByEmail(email.toString())

    //insert into user ...
    fun createUser(user: User): User = userRepository.save(user)

    //update user set ...
    fun updateUser(id: Long, updateUser: User): User {
        return if (userRepository.existsById(id)) {
            val existingUser = updateUser.copy(id = id)
            userRepository.save(existingUser)
        } else {
            throw RuntimeException("User id: $id not found")
        }
    }
    // delete from user where id = $id
    fun deleteUser(id: Long){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id)
        }else{
            throw RuntimeException("User id: $id not found")
        }
    }
}