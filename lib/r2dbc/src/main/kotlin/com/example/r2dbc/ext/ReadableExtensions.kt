package com.example.r2dbc.ext

import io.r2dbc.spi.Readable

inline fun <reified T : Any> Readable.retrieve(index: Int): T? = get(index, T::class.java)

inline fun <reified T : Any> Readable.retrieve(name: String): T? = get(name, T::class.java)
