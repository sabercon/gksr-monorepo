package com.example.r2dbc.convert

import io.netty.buffer.ByteBufAllocator
import io.r2dbc.postgresql.api.PostgresqlConnection
import io.r2dbc.postgresql.codec.CodecRegistry
import io.r2dbc.postgresql.codec.EnumCodec
import io.r2dbc.postgresql.extension.CodecRegistrar
import kotlin.reflect.KClass

/**
 * For implement Postgres enum codec extensions.
 *
 * With `r2dbc-postgresql`, extensions can be discovered using Java's `ServiceLoader` mechanism
 * (from `META-INF/services/io.r2dbc.postgresql.extension.Extension`).
 *
 * @param enumMappings Mappings from Postgres enum name to Kotlin enum class
 */
abstract class PostgresEnumCodecRegistrar(vararg enumMappings: Pair<String, KClass<out Enum<*>>>) : CodecRegistrar {

    private val delegate: CodecRegistrar = EnumCodec.builder()
        .apply { enumMappings.forEach { (name, enumClass) -> withEnum(name, enumClass.java) } }
        .build()

    override fun register(connection: PostgresqlConnection, allocator: ByteBufAllocator, registry: CodecRegistry) =
        delegate.register(connection, allocator, registry)
}
