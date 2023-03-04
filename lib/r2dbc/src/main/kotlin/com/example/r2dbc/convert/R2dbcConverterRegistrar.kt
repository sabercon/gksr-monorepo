package com.example.r2dbc.convert

import org.springframework.core.convert.converter.Converter

/**
 * Inject beans that implement this interface to provide custom converters.
 *
 * @see com.example.r2dbc.R2dbcAutoConfiguration.getCustomConverters
 */
fun interface R2dbcConverterRegistrar {

    fun converters(): List<Converter<*, *>>
}
