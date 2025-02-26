package my.project.onlineAuctionBackend.controllers

import my.project.onlineAuctionBackend.models.Product
import my.project.onlineAuctionBackend.services.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return if (products.isNotEmpty()) {
            ResponseEntity.ok(products)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<Any> {
        val product: Optional<Product> = productService.getProductById(id)
        return if (product.isPresent) {
            ResponseEntity.ok(product.get())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to "Product not found"))
        }
    }

    @GetMapping("/search")
    fun getProductsByName(@RequestParam name: String): ResponseEntity<List<Product>> {
        val products = productService.getProductsByName(name)
        return if (products.isNotEmpty()) {
            ResponseEntity.ok(products)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/category/{categoryId}")
    fun getProductsByCategory(@PathVariable categoryId: Long): ResponseEntity<List<Product>> {
        val products = productService.getProductsByCategory(categoryId)
        return if (products.isNotEmpty()) {
            ResponseEntity.ok(products)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/seller/{sellerId}")
    fun getProductsBySeller(@PathVariable sellerId: Long): ResponseEntity<List<Product>> {
        val products = productService.getProductsBySeller(sellerId)
        return if (products.isNotEmpty()) {
            ResponseEntity.ok(products)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @PostMapping
    fun createProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val createdProduct = productService.createProduct(product)
        return ResponseEntity(createdProduct, HttpStatus.CREATED)
    }

    @PostMapping("/create")
    fun createProduct(
        @RequestParam("itemName") itemName: String,
        @RequestParam("itemDescription") itemDescription: String,
        @RequestParam("itemPrice") itemPrice: Double,
        @RequestParam("itemStartDate") itemStartDate: Date,
        @RequestParam("itemEndDate") itemEndDate: Date,
        @RequestParam("categoryId") categoryId: Long,
        @RequestParam("image", required = false) image: MultipartFile?
    ): ResponseEntity<Product> {

        val imageUrl = image?.let {
            val fileName = fileStorageService.storeFile(it)
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(fileName)
                .toUriString()
        }

        val category = productService.findCategoryById(categoryId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()

        val product = Product(
            itemName = itemName,
            itemDescription = itemDescription,
            itemPrice = itemPrice,
            itemStartDate = itemStartDate,
            itemEndDate = itemEndDate,
            itemPhoto = imageUrl,
            category = category
        )

        val savedProduct = productService.saveProduct(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct)
    }

    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody product: Product): ResponseEntity<Any> {
        return try {
            val updatedProduct = productService.updateProduct(id, product)
            ResponseEntity.ok(updatedProduct)
        } catch (e: RuntimeException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to e.message))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        return try {
            productService.deleteProduct(id)
            ResponseEntity.ok(mapOf("message" to "Product deleted successfully"))
        } catch (e: RuntimeException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("message" to (e.message ?: "Product not Found")))
        }
    }
}
