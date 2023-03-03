package com.example.util

import com.google.common.hash.Hashing
import java.security.MessageDigest
import java.util.*

fun md5(input: ByteArray): String = MessageDigest.getInstance("MD5").digest(input).toHexString()

fun md5(input: String): String = md5(input.toByteArray())

fun sha256(input: ByteArray): String = MessageDigest.getInstance("SHA-256").digest(input).toHexString()

fun sha256(input: String): String = sha256(input.toByteArray())

fun hmacSha1(key: ByteArray, input: ByteArray): String = Hashing.hmacSha1(key).hashBytes(input).asBytes().toHexString()

fun hmacSha1(key: String, input: String): String = hmacSha1(key.toByteArray(), input.toByteArray())

fun randomKey(): String = md5(UUID.randomUUID().toString())
