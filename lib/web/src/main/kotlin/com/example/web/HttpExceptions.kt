package com.example.web

import org.springframework.http.HttpStatus

class HttpException(val status: HttpStatus, val code: Int, message: String) : RuntimeException(message)

interface ErrorCode {
    val status: HttpStatus
    val code: Int
    val message: String
    fun error(message: String = this.message) = HttpException(status, code, message)
    fun throws(message: String = this.message): Nothing = throw error(message)
}

enum class BaseErrorCode(
    override val status: HttpStatus,
    override val code: Int,
    override val message: String,
) : ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "Unauthorized"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Not Found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),

    INVALID_PARAMS(HttpStatus.BAD_REQUEST, 10001, "Invalid Params"),
}
