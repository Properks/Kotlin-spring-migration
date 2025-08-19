package org.jeongmo.practice.global.api.payload

import org.jeongmo.practice.global.util.logger
import org.namul.api.payload.code.dto.ErrorReasonDTO
import org.namul.api.payload.log.ExceptionAdviceLogger
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class CustomExceptionAdviceLogger : ExceptionAdviceLogger {

    private val log = logger()

    override fun <E : Exception?, R : ErrorReasonDTO?> log(p0: E, p1: R, p2: Any?) {
        log.warn("msg: ${p1.toString()}, exception: ${p0?.message ?: "Unknown"}")
    }
}