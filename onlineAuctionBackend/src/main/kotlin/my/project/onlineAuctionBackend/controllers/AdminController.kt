package my.project.onlineAuctionBackend.controllers

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
class AdminController {
    @GetMapping("/dashboard")
    fun adminDashboard(): ResponseEntity<String> {
        return ResponseEntity.ok("Welcome Admin!")
    }
}