package com.example.r2dbc

import org.springframework.data.domain.Sort
import kotlin.reflect.KProperty

fun asc(vararg properties: String): Sort = Sort.by(properties.map { Sort.Order.asc(it) })

fun desc(vararg properties: String): Sort = Sort.by(properties.map { Sort.Order.desc(it) })

fun asc(vararg properties: KProperty<*>): Sort = Sort.by(properties.map { Sort.Order.asc(it.name) })

fun desc(vararg properties: KProperty<*>): Sort = Sort.by(properties.map { Sort.Order.desc(it.name) })
