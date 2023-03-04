package com.example.demo

import com.example.test.container.MongoDB
import com.example.test.container.Postgres
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureWebTestClient
class ApplicationStartupTest(client: WebTestClient) : FunSpec({

    test("Health check") {
        client.get().uri("/actuator/health")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("status").isEqualTo("UP")
    }
}) {
    companion object {
        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            Postgres.registerR2dbc(registry)
            Postgres.registerFlyway(registry)
            MongoDB.register(registry)
        }
    }
}
