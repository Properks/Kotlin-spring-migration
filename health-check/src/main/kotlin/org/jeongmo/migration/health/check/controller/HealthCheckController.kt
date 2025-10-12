package org.jeongmo.migration.health.check.controller

import org.namul.api.payload.response.DefaultResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health-check")
class HealthCheckController {

    @GetMapping
    fun healthCheck(): DefaultResponse<String> =
        DefaultResponse.ok("Health-check")
}