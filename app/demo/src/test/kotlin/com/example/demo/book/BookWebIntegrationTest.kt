package com.example.demo.book

import com.example.test.container.MongoDB
import com.example.test.container.Postgres
import com.example.test.expectCode
import com.example.test.expectData
import com.example.test.expectStatus
import com.example.util.now
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.date.shouldNotBeAfter
import io.kotest.matchers.longs.shouldBePositive
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.orNull
import io.kotest.property.arbitrary.positiveLong
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Duration

@SpringBootTest
@AutoConfigureWebTestClient
class BookWebIntegrationTest(client: WebTestClient, repository: BookRepository) : FunSpec({

    afterEach { Postgres.clear() }

    context("POST /book") {

        test("Returns the created book") {
            checkAll(10, Arb.string(), Arb.string(), Arb.positiveLong()) { title, author, price ->
                val response = client.post().uri("/book")
                    .bodyValue(BookCreateInput(title, author, price))
                    .expectData<BookModel>()

                response.apply {
                    this.id.shouldBePositive()
                    this.title shouldBe title
                    this.author shouldBe author
                    this.price shouldBe price
                    createdAt shouldNotBeAfter now()
                    createdAt shouldBeAfter now() - Duration.ofMinutes(1)
                    updatedAt shouldBe createdAt
                }

                repository.findById(response.id)
                    .apply { shouldNotBeNull() }
                    .let { BookModel.from(it!!) }
                    .shouldBe(response)
            }
        }
    }

    context("PUT /book/{id}") {

        test("Returns the updated book") {
            checkAll(
                10,
                Arb.string().orNull(),
                Arb.string().orNull(),
                Arb.positiveLong().orNull(),
            ) { title, author, price ->
                val book = book().let { repository.save(it) }

                val response = client.put().uri("/book/{0}", book.id)
                    .bodyValue(BookUpdateInput(title, author, price))
                    .expectData<BookModel>()

                response.apply {
                    this.id shouldBe book.id
                    this.title shouldBe (title ?: book.title)
                    this.price shouldBe (price ?: book.price)
                    createdAt shouldBe book.createdAt
                    updatedAt shouldNotBeAfter now()
                    updatedAt shouldBeAfter now() - Duration.ofMinutes(1)
                }

                repository.findById(response.id)
                    .apply { shouldNotBeNull() }
                    .let { BookModel.from(it!!) }
                    .shouldBe(response)
            }
        }

        test("Returns errors if the book not found") {
            val id = Arb.long().next()

            client.put().uri("/book/{0}", id)
                .bodyValue(BookUpdateInput(Arb.string().next(), Arb.string().next(), Arb.positiveLong().next()))
                .expectStatus(HttpStatus.NOT_FOUND)

            repository.findById(id).shouldBeNull()
        }
    }

    context("DELETE /book/{id}") {

        test("Deletes the book if found") {
            val book = book()

            client.delete().uri("/book/{0}", book.id).expectStatus(HttpStatus.NO_CONTENT)

            repository.findById(book.id).shouldBeNull()
        }

        test("Does nothing if the book not found") {
            val id = Arb.long().next()

            client.delete().uri("/book/{0}", id).expectStatus(HttpStatus.NO_CONTENT)

            repository.findById(id).shouldBeNull()
        }
    }

    context("GET /book/{id}") {

        test("Returns the book if found") {
            val book = book().let { repository.save(it) }

            client.get().uri("/book/{0}", book.id)
                .expectData<BookModel>()
                .apply {
                    id shouldBe book.id
                    title shouldBe book.title
                    author shouldBe book.author
                    price shouldBe book.price
                    createdAt shouldBe book.createdAt
                    updatedAt shouldBe book.updatedAt
                }
        }

        test("Returns errors if the book not found") {
            val id = Arb.long().next()

            client.get().uri("/book/{0}", id).expectStatus(HttpStatus.NOT_FOUND)
        }
    }

    context("GET /books") {

        test("Returns all books written by the given author") {
            val author = Arb.string().next()
            val books = generateSequence { book(author = author) }
                .take(Arb.int(1..10).next())
                .asFlow()
                .let { repository.saveAll(it) }.toList()

            client.get().uri("/books?author={0}", author)
                .expectData<List<BookModel>>()
                .shouldContainExactly(books.map { BookModel.from(it) })
        }

        test("Ignores books written by other authors") {
            val author = Arb.string().next()
            repository.save(book())

            client.get().uri("/books?author={0}", author)
                .expectData<List<BookModel>>()
                .shouldBeEmpty()
        }

        test("Returns errors if the param `author` not provided") {
            client.get().uri("/books").expectCode(HttpStatus.BAD_REQUEST) shouldBe 10001
        }
    }
}) {
    companion object {
        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            Postgres.registerR2dbc(registry)
            Postgres.registerFlyway(registry)
            MongoDB.register(registry)
        }
    }
}
