package org.jeongmo.practice.global.common.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.namul.api.payload.code.DefaultResponseErrorCode
import org.namul.api.payload.error.exception.ServerApplicationException
import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Health Check API")
@RestController
@RequestMapping("/health-check")
class HealthCheckController {

    @GetMapping
    fun healthCheck(): DefaultResponse<String> = DefaultResponse.ok("성공")

    @GetMapping("/server-error")
    fun serverError(): DefaultResponse<Unit> = throw ServerApplicationException(DefaultResponseErrorCode._BAD_REQUEST)

}