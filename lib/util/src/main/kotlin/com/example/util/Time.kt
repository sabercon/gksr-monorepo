package com.example.util

import java.time.LocalDateTime

val EPOCH: LocalDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0)

fun now(): LocalDateTime = LocalDateTime.now()
