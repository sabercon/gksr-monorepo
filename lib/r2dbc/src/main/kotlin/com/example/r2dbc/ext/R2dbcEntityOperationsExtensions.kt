package com.example.r2dbc.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityOperations
import org.springframework.data.r2dbc.core.allAndAwait
import org.springframework.data.r2dbc.core.applyAndAwait
import org.springframework.data.r2dbc.core.awaitCount
import org.springframework.data.r2dbc.core.awaitExists
import org.springframework.data.r2dbc.core.awaitFirst
import org.springframework.data.r2dbc.core.awaitFirstOrNull
import org.springframework.data.r2dbc.core.awaitOne
import org.springframework.data.r2dbc.core.awaitOneOrNull
import org.springframework.data.r2dbc.core.delete
import org.springframework.data.r2dbc.core.flow
import org.springframework.data.r2dbc.core.select
import org.springframework.data.r2dbc.core.update
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.query.Update

suspend fun <T : Any> R2dbcEntityOperations.insertOne(entity: T): T {
    return insert(entity).awaitSingle()
}

suspend inline fun <reified T : Any> R2dbcEntityOperations.updateAll(criteria: Criteria, update: Update): Long {
    return update<T>().matching(criteria).applyAndAwait(update)
}

suspend inline fun <reified T : Any> R2dbcEntityOperations.deleteAll(criteria: Criteria): Long {
    return delete<T>().matching(criteria).allAndAwait()
}

suspend inline fun <reified T : Any> R2dbcEntityOperations.count(criteria: Criteria): Long {
    return select<T>().matching(criteria).awaitCount()
}

suspend inline fun <reified T : Any> R2dbcEntityOperations.exists(criteria: Criteria): Boolean {
    return select<T>().matching(criteria).awaitExists()
}

suspend inline fun <reified T : Any> R2dbcEntityOperations.findOne(criteria: Criteria): T {
    return select<T>().matching(criteria).awaitOne()
}

suspend inline fun <reified T : Any> R2dbcEntityOperations.findOneOrNull(criteria: Criteria): T? {
    return select<T>().matching(criteria).awaitOneOrNull()
}

suspend inline fun <reified T : Any> R2dbcEntityOperations.findFirst(criteria: Criteria): T {
    return select<T>().matching(criteria).awaitFirst()
}

suspend inline fun <reified T : Any> R2dbcEntityOperations.findFirstOrNull(criteria: Criteria): T? {
    return select<T>().matching(criteria).awaitFirstOrNull()
}

inline fun <reified T : Any> R2dbcEntityOperations.findAll(criteria: Criteria): Flow<T> {
    return select<T>().matching(criteria).flow()
}

inline fun <reified T : Any> R2dbcEntityOperations.findAll(query: Query): Flow<T> {
    return select<T>().matching(query).flow()
}
