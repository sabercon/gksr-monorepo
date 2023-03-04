package com.example.demo.book

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface BookRepository : CoroutineCrudRepository<Book, Long> {

    fun findByAuthor(author: String): Flow<Book>
}
