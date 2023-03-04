package com.example.web

import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest
@AutoConfigureWebTestClient
class ActuatorRouterTest(client: WebTestClient) : FunSpec({

    context("GET /actuator/health") {
        test("Returns `UP`") {
            client.get()
                .uri("/actuator/health")
                .exchange()
                .expectStatus().isOk
                .expectBody<String>()
                .isEqualTo("""{"status":"UP"}""")
        }
    }
})
