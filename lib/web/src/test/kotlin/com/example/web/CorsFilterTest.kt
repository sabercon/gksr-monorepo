package com.example.web

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.testContextManager
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodilessEntity

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CorsFilterTest : FunSpec({

    context("corsFilter") {
        val context = (testContextManager().testContext.applicationContext as ReactiveWebServerApplicationContext)
        val port = context.webServer.port
        val client = WebClient.create("http://localhost:$port/some-path")

        test("Returns CORS headers to permit all cross-origin requests by default") {
            HttpMethod.values().forEach { method ->
                client.options()
                    .header(HttpHeaders.ORIGIN, "https://example.com")
                    .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, method.name())
                    .header("Some-Header", "xxx")
                    .retrieve()
                    .awaitBodilessEntity()
                    .headers
                    .apply {
                        accessControlAllowOrigin shouldBe "https://example.com"
                        accessControlAllowMethods shouldBe listOf(method)
                    }
            }
        }
    }
})
