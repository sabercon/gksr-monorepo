package com.example.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import org.bson.types.Decimal128
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.convert.converter.Converter
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions.MongoConverterConfigurationAdapter
import java.math.BigDecimal

class ReactiveMongoAutoConfiguration(private val properties: MongoProperties) : AbstractReactiveMongoConfiguration() {

    override fun getDatabaseName(): String = properties.database

    override fun autoIndexCreation() = true

    @Bean
    override fun reactiveMongoClient() = super.reactiveMongoClient()

    override fun configureClientSettings(builder: MongoClientSettings.Builder) {
        builder.applyConnectionString(ConnectionString(properties.uri))
    }

    @Suppress("ObjectLiteralToLambda")
    override fun configureConverters(converterConfigurationAdapter: MongoConverterConfigurationAdapter) {
        val decimal128ToBigDecimalConverter = object : Converter<Decimal128, BigDecimal> {
            override fun convert(source: Decimal128) = source.bigDecimalValue()
        }
        val bigDecimalToDecimal128Converter = object : Converter<BigDecimal, Decimal128> {
            override fun convert(source: BigDecimal) = Decimal128(source)
        }

        converterConfigurationAdapter
            .registerConverter(decimal128ToBigDecimalConverter)
            .registerConverter(bigDecimalToDecimal128Converter)
    }
}
