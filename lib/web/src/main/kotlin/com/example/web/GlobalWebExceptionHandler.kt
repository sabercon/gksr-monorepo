package com.example.web

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.server.ResponseStatusException

class GlobalWebExceptionHandler(
    errorAttributes: ErrorAttributes,
    resources: WebProperties.Resources,
    applicationContext: ApplicationContext,
) : AbstractErrorWebExceptionHandler(errorAttributes, resources, applicationContext) {

    override fun getRoutingFunction(errorAttributes: ErrorAttributes) = RouterFunctions.route(RequestPredicates.all()) {
        when (val error = errorAttributes.getError(it)) {
            is HttpException -> status(error.status).bodyValue(mapOf("code" to error.code, "message" to error.message))
            is ResponseStatusException -> status(error.statusCode).build()
            else -> status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}
