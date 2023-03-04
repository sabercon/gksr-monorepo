package com.example.web

import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.result.view.ViewResolver
import org.springframework.web.server.WebFilter
import java.util.stream.Collectors

@Configuration
@EnableConfigurationProperties(CorsProperties::class)
class WebAutoConfiguration {

    @Bean
    fun corsFilter(properties: CorsProperties): WebFilter {
        val config = CorsConfiguration().apply {
            allowedOriginPatterns = properties.allowedOriginPatterns
            allowedMethods = properties.allowedMethods
            allowedHeaders = properties.allowedHeaders
            allowCredentials = properties.allowCredentials
        }

        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }

        return CorsWebFilter(source)
    }

    /**
     * Adds `@Order(-2)` to give it a higher priority than
     * [org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler].
     *
     * @see org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration.errorWebExceptionHandler
     */
    @Bean
    @Order(-2)
    fun exceptionHandler(
        errorAttributes: ErrorAttributes,
        webProperties: WebProperties,
        applicationContext: ApplicationContext,
        viewResolvers: ObjectProvider<ViewResolver>,
        serverCodecConfigurer: ServerCodecConfigurer,
    ): GlobalWebExceptionHandler {
        val exceptionHandler =
            GlobalWebExceptionHandler(errorAttributes, webProperties.resources, applicationContext)
        exceptionHandler.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()))
        exceptionHandler.setMessageWriters(serverCodecConfigurer.writers)
        exceptionHandler.setMessageReaders(serverCodecConfigurer.readers)
        return exceptionHandler
    }
}
