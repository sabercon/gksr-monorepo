package com.example.r2dbc

import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.getProperty

/**
 * Converts R2DBC URL to flyway data source properties.
 *
 * The properties are added at the last so that they can be overridden by other configurations.
 */
class R2dbcToFlywayDataSourceListener : ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    override fun onApplicationEvent(event: ApplicationEnvironmentPreparedEvent) {
        val environment = event.environment
        if (environment.getProperty<Boolean>("spring.flyway.enabled") != true) return
        val r2dbcUrl = environment.getProperty("spring.r2dbc.url") ?: return
        val (jdbcUrl, user, password) = parseJdbcConfig(r2dbcUrl)

        val flywayDataSource = mapOf(
            "spring.flyway.url" to jdbcUrl,
            "spring.flyway.user" to user,
            "spring.flyway.password" to password,
        )
        environment.propertySources.addLast(MapPropertySource("flywayDataSource", flywayDataSource))
    }

    /**
     * @return jdbcUrl, user, password
     */
    private fun parseJdbcConfig(r2dbcUrl: String): Triple<String, String, String> {
        val options = ConnectionFactoryOptions.parse(r2dbcUrl)

        val driver = options.getValue(ConnectionFactoryOptions.DRIVER) as String
        val host = options.getValue(ConnectionFactoryOptions.HOST) as String
        val port = options.getValue(ConnectionFactoryOptions.PORT) as Int?
        val portString = if (port != null) ":$port" else ""
        val database = options.getValue(ConnectionFactoryOptions.DATABASE) as String
        val jdbcUrl = "jdbc:$driver://$host$portString/$database"

        val user = options.getValue(ConnectionFactoryOptions.USER) as String
        val password = options.getValue(ConnectionFactoryOptions.PASSWORD) as String

        return Triple(jdbcUrl, user, password)
    }
}
