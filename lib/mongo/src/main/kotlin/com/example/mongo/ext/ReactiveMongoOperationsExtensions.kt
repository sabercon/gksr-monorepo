package com.example.mongo.ext

import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.allAndAwait
import org.springframework.data.mongodb.core.awaitCount
import org.springframework.data.mongodb.core.awaitExists
import org.springframework.data.mongodb.core.awaitFirst
import org.springframework.data.mongodb.core.awaitFirstOrNull
import org.springframework.data.mongodb.core.awaitOne
import org.springframework.data.mongodb.core.awaitOneOrNull
import org.springframework.data.mongodb.core.findAndRemoveAsFlow
import org.springframework.data.mongodb.core.firstAndAwait
import org.springframework.data.mongodb.core.flow
import org.springframework.data.mongodb.core.query
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.remove
import org.springframework.data.mongodb.core.update
import org.springframework.data.mongodb.core.upsertAndAwait

suspend fun <T : Any> ReactiveMongoOperations.insertOne(entity: T): T {
    return insert(entity).awaitSingle()
}

suspend inline fun <reified T : Any> ReactiveMongoOperations.updateFirst(
    criteria: Criteria,
    update: Update,
): UpdateResult {
    return update<T>().matching(criteria).apply(update).firstAndAwait()
}

suspend inline fun <reified T : Any> ReactiveMongoOperations.updateAll(
    criteria: Criteria,
    update: Update,
): UpdateResult {
    return update<T>().matching(criteria).apply(update).allAndAwait()
}

suspend inline fun <reified T : Any> ReactiveMongoOperations.upsert(
    criteria: Criteria,
    update: Update,
): UpdateResult {
    return update<T>().matching(criteria).apply(update).upsertAndAwait()
}

suspend inline fun <reified T : Any> ReactiveMongoOperations.removeAll(criteria: Criteria): DeleteResult {
    return remove<T>().matching(criteria).allAndAwait()
}

inline fun <reified T : Any> ReactiveMongoOperations.findAndRemove(criteria: Criteria): Flow<T> {
    return remove<T>().matching(criteria).findAndRemoveAsFlow()
}

suspend inline fun <reified T : Any> ReactiveMongoOperations.count(criteria: Criteria): Long {
    return query<T>().matching(criteria).awaitCount()
}

suspend inline fun <reified T : Any> ReactiveMongoOperations.exists(criteria: Criteria): Boolean {
    return query<T>().matching(criteria).awaitExists()
}

suspend inline fun <reified T : Any> ReactiveMongoOperations.findOne(criteria: Criteria): T {
    return query<T>().matching(criteria).awaitOne()
}

suspend inline fun <reified T : Any> ReactiveMongoOperations.findOneOrNull(criteria: Criteria): T? {
    return query<T>().matching(criteria).awaitOneOrNull()
}

suspend inline fun <reified T : Any> ReactiveMongoOperations.findFirst(criteria: Criteria): T {
    return query<T>().matching(criteria).awaitFirst()
}

suspend inline fun <reified T : Any> ReactiveMongoOperations.findFirstOrNull(criteria: Criteria): T? {
    return query<T>().matching(criteria).awaitFirstOrNull()
}

inline fun <reified T : Any> ReactiveMongoOperations.findAll(criteria: Criteria): Flow<T> {
    return query<T>().matching(criteria).flow()
}

inline fun <reified T : Any> ReactiveMongoOperations.findAll(query: Query): Flow<T> {
    return query<T>().matching(query).flow()
}
