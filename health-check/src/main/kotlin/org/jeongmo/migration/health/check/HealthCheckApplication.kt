package org.jeongmo.migration.health.check

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.jeongmo.migration.health.check", "org.jeongmo.migration.common.config"])
class HealthCheckApplication

fun main(args: Array<String>) {
    runApplication<HealthCheckApplication>(*args)
}

// 클라우드 config 사용 안함...