package org.jeongmo.migration.common.config

import org.namul.api.payload.error.AdditionalExceptionHandler
import org.namul.api.payload.error.DefaultExceptionAdviceConfigurer
import org.namul.api.payload.error.ExceptionAdvice
import org.namul.api.payload.writer.FailureResponseWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiPayloadConfig {

    @Bean
    fun exceptionAdvice(
        failureResponseWriter: FailureResponseWriter,
        additionalExceptionHandlers: List<AdditionalExceptionHandler>,
    ): ExceptionAdvice {
        return DefaultExceptionAdviceConfigurer(failureResponseWriter)
            .addAdditionalExceptionHandlers(additionalExceptionHandlers)
            .build()
    }
}