package com.example.demo.book

import com.example.util.now
import io.kotest.property.Arb
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.positiveLong
import io.kotest.property.arbitrary.string
import java.time.LocalDateTime

fun book(
    title: String = Arb.string().next(),
    author: String = Arb.string().next(),
    price: Long = Arb.positiveLong().next(),
    createdAt: LocalDateTime = now(),
    updatedAt: LocalDateTime = now(),
) = Book(
    title = title,
    author = author,
    price = price,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
