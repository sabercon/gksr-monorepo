package com.example.web

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.json
import java.net.URI

suspend fun CoRouterFunctionDsl.empty(): ServerResponse {
    return noContent().buildAndAwait()
}

suspend fun CoRouterFunctionDsl.json(body: Any): ServerResponse {
    return ok().json().bodyValueAndAwait(mapOf("data" to body))
}

suspend fun CoRouterFunctionDsl.json(flow: Flow<*>): ServerResponse {
    return json(flow.toList())
}

suspend fun CoRouterFunctionDsl.redirect(url: String): ServerResponse {
    return status(HttpStatus.FOUND).location(URI.create(url)).buildAndAwait()
}
