package com.example.test.container

import com.example.util.ext.await
import com.mongodb.ConnectionString
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.MongoDBContainer

object MongoDB {

    val CONTAINER = MongoDBContainer("mongo").apply { start() }

    private val TEMPLATE =
        ReactiveMongoTemplate(SimpleReactiveMongoDatabaseFactory(ConnectionString(CONTAINER.replicaSetUrl)))

    fun register(registry: DynamicPropertyRegistry) {
        registry.add("spring.data.mongodb.uri") { CONTAINER.replicaSetUrl }
        registry.add("spring.data.mongodb.database") { "test" }
    }

    suspend fun clear() {
        TEMPLATE.collectionNames
            .flatMap { TEMPLATE.remove(Query(), it) }
            .await()
    }
}
