package org.jeongmo.practice.global.config

import org.namul.api.payload.code.dto.supports.DefaultResponseErrorReasonDTO
import org.namul.api.payload.error.configurer.DefaultExceptionAdviceConfigurer
import org.namul.api.payload.writer.FailureResponseWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExceptionAdviceConfig {

    @Bean
    fun exceptionAdviceConfigurer(failureResponseWriter : FailureResponseWriter<DefaultResponseErrorReasonDTO>) = DefaultExceptionAdviceConfigurer(failureResponseWriter)
}