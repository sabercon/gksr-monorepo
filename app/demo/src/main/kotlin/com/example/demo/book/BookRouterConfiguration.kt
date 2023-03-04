package com.example.demo.book

import com.example.web.BaseErrorCode
import com.example.web.empty
import com.example.web.json
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.queryParamOrNull

@Configuration
class BookRouterConfiguration {

    @Bean
    fun bookRouter(bookService: BookService) = coRouter {
        POST("/book") { request ->
            request.awaitBody<BookCreateInput>()
                .let { bookService.createBook(it) }
                .let { json(it) }
        }

        PUT("/book/{id}") { request ->
            val id = request.pathVariable("id").toLong()

            request.awaitBody<BookUpdateInput>()
                .let { bookService.updateBook(id, it) }
                .let { json(it) }
        }

        DELETE("/book/{id}") { request ->
            val id = request.pathVariable("id").toLong()
            bookService.deleteBook(id)
            empty()
        }

        GET("/book/{id}") { request ->
            val id = request.pathVariable("id").toLong()
            json(bookService.getBook(id))
        }

        GET("/books") { request ->
            val author = request.queryParamOrNull("author") ?: BaseErrorCode.INVALID_PARAMS.throws()
            json(bookService.listBook(author))
        }
    }
}
