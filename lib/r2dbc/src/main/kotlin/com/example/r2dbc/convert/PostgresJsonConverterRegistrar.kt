package com.example.r2dbc.convert

import com.example.util.toJson
import com.example.util.toJsonNode
import com.fasterxml.jackson.databind.JsonNode
import io.r2dbc.postgresql.codec.Json
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

/**
 * This class can be injected as a bean to enable JSONB and Jackson conversions.
 */
class PostgresJsonConverterRegistrar : R2dbcConverterRegistrar {

    @Suppress("ObjectLiteralToLambda")
    override fun converters(): List<Converter<*, *>> {
        val reader = @ReadingConverter object : Converter<Json, JsonNode> {
            override fun convert(source: Json) = source.asString().toJsonNode()
        }
        val writer = @WritingConverter object : Converter<JsonNode, Json> {
            override fun convert(source: JsonNode) = Json.of(source.toJson())
        }

        return listOf(reader, writer)
    }
}
