package com.example.util

import java.net.URLEncoder
import java.util.*

fun urlEncode(input: String): String = URLEncoder.encode(input, Charsets.UTF_8)

fun base64(input: ByteArray): String = Base64.getEncoder().encode(input).toString(Charsets.UTF_8)

fun base64(input: String): String = base64(input.toByteArray())

fun base64Url(input: ByteArray): String = Base64.getUrlEncoder().encode(input).toString(Charsets.UTF_8)

fun base64Url(input: String): String = base64Url(input.toByteArray())

fun ByteArray.toHexString(): String = joinToString(separator = "") { "%02x".format(it) }
