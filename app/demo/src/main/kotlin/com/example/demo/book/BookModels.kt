package com.example.demo.book

import java.time.LocalDateTime

data class BookCreateInput(
    val title: String,
    val author: String,
    val price: Long,
) {
    fun create() = Book(
        title = title,
        author = author,
        price = price,
    )
}

data class BookUpdateInput(
    val title: String? = null,
    val author: String? = null,
    val price: Long? = null,
) {
    fun update(book: Book) = book.copy(
        title = title ?: book.title,
        author = author ?: book.author,
        price = price ?: book.price,
    )
}

data class BookModel(
    val id: Long,
    val title: String,
    val author: String,
    val price: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(book: Book) = BookModel(
            id = book.id,
            title = book.title,
            author = book.author,
            price = book.price,
            createdAt = book.createdAt,
            updatedAt = book.updatedAt,
        )
    }
}
