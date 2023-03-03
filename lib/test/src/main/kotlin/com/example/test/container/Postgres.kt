package com.example.test.container

import com.example.util.ext.await
import io.r2dbc.spi.ConnectionFactories
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.PostgreSQLContainer

object Postgres {

    val CONTAINER = PostgreSQLContainer("postgres").apply { start() }

    private val TEMPLATE = R2dbcEntityTemplate(ConnectionFactories.get(r2dbcUrl()))

    fun r2dbcUrl(): String {
        return CONTAINER.run { "r2dbc:postgresql://$username:$password@$host:$firstMappedPort/$databaseName" }
    }

    fun jdbcUrl(): String {
        return CONTAINER.run { "jdbc:postgresql://$host:$firstMappedPort/$databaseName" }
    }

    fun registerR2dbc(registry: DynamicPropertyRegistry) {
        registry.add("spring.r2dbc.url") { r2dbcUrl() }
    }

    fun registerFlyway(registry: DynamicPropertyRegistry) {
        registry.add("spring.flyway.url") { jdbcUrl() }
        registry.add("spring.flyway.user") { CONTAINER.username }
        registry.add("spring.flyway.password") { CONTAINER.password }
    }

    /**
     * Transactional tests are not yet supported in R2DBC.
     * Therefore, we have to clean all tables to rollback modifications.
     * This approach is not efficient and prevents us from running tests concurrently.
     */
    suspend fun clear() {
        TEMPLATE.databaseClient
            .sql(
                """SELECT table_name FROM information_schema.tables
                    WHERE table_schema = 'public' AND table_type != 'VIEW'
                    AND table_name != 'flyway_schema_history'"""
            )
            .map { r -> r[0] as String }
            .all()
            .flatMap { TEMPLATE.databaseClient.sql("""DELETE FROM "$it"""").then() }
            .await()
    }
}
