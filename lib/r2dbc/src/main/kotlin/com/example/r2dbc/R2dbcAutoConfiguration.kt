package com.example.r2dbc

import com.example.r2dbc.convert.R2dbcConverterRegistrar
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.relational.RelationalManagedTypes
import org.springframework.data.relational.core.mapping.NamingStrategy
import java.util.*

@EnableR2dbcAuditing
class R2dbcAutoConfiguration(private val converterRegistrars: List<R2dbcConverterRegistrar>) :
    AbstractR2dbcConfiguration() {

    /**
     * Not used at the moment.
     */
    override fun connectionFactory() = throw UnsupportedOperationException()

    override fun r2dbcMappingContext(
        namingStrategy: Optional<NamingStrategy>,
        r2dbcCustomConversions: R2dbcCustomConversions,
        r2dbcManagedTypes: RelationalManagedTypes,
    ) = super.r2dbcMappingContext(namingStrategy, r2dbcCustomConversions, r2dbcManagedTypes)
        .apply { isForceQuote = true }

    override fun getCustomConverters() = converterRegistrars.flatMap { it.converters() }
}
