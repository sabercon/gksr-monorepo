package com.example.web

import org.springframework.web.reactive.function.server.ServerRequest

fun ServerRequest.ip(): String = remoteAddress()
    .map { it.address }
    .map { it.hostAddress }
    .orElse("UNKNOWN")
