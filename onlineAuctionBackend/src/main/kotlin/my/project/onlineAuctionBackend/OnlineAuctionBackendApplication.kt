package my.project.onlineAuctionBackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@SpringBootApplication
@EnableJpaRepositories("my.project.onlineAuctionBackend.repositories")
@EntityScan("my.project.onlineAuctionBackend.models")


class OnlineAuctionBackendApplication

fun main(args: Array<String>) {
	runApplication<OnlineAuctionBackendApplication>(*args)
}
