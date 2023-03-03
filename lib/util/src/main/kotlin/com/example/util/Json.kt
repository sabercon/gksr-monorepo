package com.example.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import com.fasterxml.jackson.module.kotlin.readValue

/**
 * Note that [JSON] is global and any modification is forbidden.
 *
 * Use [com.fasterxml.jackson.databind.cfg.MapperBuilder] to mutate a mapper.
 */
val JSON: JsonMapper = jacksonMapperBuilder()
    .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .addModules(JavaTimeModule())
    .build()

fun Any.toJson(): String = JSON.writeValueAsString(this)

inline fun <reified T : Any> String.toJsonObject(): T = JSON.readValue(this)

fun String.toJsonNode(): JsonNode = JSON.readTree(this)

inline fun <reified T : Any> JsonNode.toObject(): T = JSON.convertValue(this)
