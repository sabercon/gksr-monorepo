package com.example.util.ext

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

suspend fun <T> Mono<T>.await() {
    awaitSingleOrNull()
}

suspend fun <T> Flux<T>.await() {
    then().awaitSingleOrNull()
}

suspend fun <T> Flux<T>.awaitList(): List<T> {
    return collectList().awaitSingle()
}
