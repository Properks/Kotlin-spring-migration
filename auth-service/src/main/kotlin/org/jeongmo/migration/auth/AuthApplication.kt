package org.jeongmo.migration.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "org.jeongmo.migration.auth",
        "org.jeongmo.migration.common.token",
        "org.jeongmo.migration.common.enums.member",
    ]
)
class AuthApplication

fun main(args: Array<String>) {
    runApplication<AuthApplication>(*args)
}