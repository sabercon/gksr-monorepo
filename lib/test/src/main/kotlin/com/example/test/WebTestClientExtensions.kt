package com.example.test

import com.example.util.toObject
import com.fasterxml.jackson.databind.JsonNode
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

fun WebTestClient.RequestHeadersSpec<*>.expectStatus(status: HttpStatus): WebTestClient.ResponseSpec {
    return exchange().expectStatus().isEqualTo(status)
}

inline fun <reified T : Any> WebTestClient.RequestHeadersSpec<*>.expectData(): T {
    return expectStatus(HttpStatus.OK)
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody<JsonNode>()
        .returnResult()
        .responseBody!!["data"]
        .toObject()
}

fun WebTestClient.RequestHeadersSpec<*>.expectCode(status: HttpStatus = HttpStatus.BAD_REQUEST): Int {
    return expectStatus(status)
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody<JsonNode>()
        .returnResult()
        .responseBody!!["code"]
        .apply { isInt shouldBe true }
        .intValue()
}
