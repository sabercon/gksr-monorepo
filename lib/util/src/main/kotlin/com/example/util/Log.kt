package com.example.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.invoke.MethodHandles
import kotlin.reflect.KClass

val ROOT_LOGGER: Logger = logger("ROOT")

fun logger(name: String): Logger = LoggerFactory.getLogger(name)

/**
 * Returns a logger named corresponding to [clazz].
 * Uses the enclosing class if [clazz] is a companion object.
 */
fun logger(clazz: KClass<*>): Logger = LoggerFactory.getLogger(actualJavaClass(clazz))

private fun actualJavaClass(clazz: KClass<*>): Class<*> {
    return if (clazz.isCompanion) clazz.java.enclosingClass else clazz.java
}

/**
 * Returns a logger named corresponding to the caller class.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun logger(): Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
