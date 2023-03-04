package com.example.demo.book

import com.example.util.EPOCH
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class Book(
    @Id val id: Long = 0,
    val title: String,
    val author: String,
    val price: Long,
    @CreatedDate val createdAt: LocalDateTime = EPOCH,
    @LastModifiedDate val updatedAt: LocalDateTime = EPOCH,
)
