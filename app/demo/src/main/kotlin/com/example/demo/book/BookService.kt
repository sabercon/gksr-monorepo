package com.example.demo.book

import com.example.web.BaseErrorCode
import org.springframework.stereotype.Service

@Service
class BookService(private val repository: BookRepository) {

    suspend fun createBook(input: BookCreateInput): BookModel {
        return input.create()
            .let { repository.save(it) }
            .let { BookModel.from(it) }
    }

    suspend fun updateBook(id: Long, input: BookUpdateInput): BookModel {
        return (repository.findById(id) ?: BaseErrorCode.NOT_FOUND.throws())
            .let { input.update(it) }
            .let { repository.save(it) }
            .let { BookModel.from(it) }
    }

    suspend fun deleteBook(id: Long) = repository.deleteById(id)

    suspend fun getBook(id: Long) = repository.findById(id) ?: BaseErrorCode.NOT_FOUND.throws()

    fun listBook(author: String) = repository.findByAuthor(author)
}
