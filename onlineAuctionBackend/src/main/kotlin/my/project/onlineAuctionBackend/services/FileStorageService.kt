package my.project.onlineAuctionBackend.services

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class FileStorageService {

    private val uploadDir: Path = Paths.get("uploads")

    init {
        Files.createDirectories(uploadDir)
    }

    fun storeFile(file: MultipartFile): String {
        val fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + "_" + file.originalFilename)

        val targetLocation = uploadDir.resolve(fileName)
        Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)

        return fileName
    }

    fun loadFileAsResource(fileName: String): Resource {
        val filePath = uploadDir.resolve(fileName).normalize()
        return ClassPathResource(filePath.toString())
    }

    fun deleteFile(fileName: String): Boolean {
        val filePath = uploadDir.resolve(fileName)
        return Files.deleteIfExists(filePath)
    }
}
