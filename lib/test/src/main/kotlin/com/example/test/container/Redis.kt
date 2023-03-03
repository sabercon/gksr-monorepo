package com.example.test.container

import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.GenericContainer

object Redis {

    val CONTAINER = GenericContainer("redis").apply {
        withExposedPorts(6379)
        start()
    }

    fun url(): String {
        return CONTAINER.run { "redis://$host:$firstMappedPort" }
    }

    fun register(registry: DynamicPropertyRegistry) {
        registry.add("spring.redis.url") { url() }
    }

    fun clear() {
        CONTAINER.execInContainer("redis-cli", "flushall")
    }
}
