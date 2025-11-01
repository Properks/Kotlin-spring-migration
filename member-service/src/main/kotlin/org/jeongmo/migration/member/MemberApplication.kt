package org.jeongmo.migration.member

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = [
    "org.jeongmo.migration.member",
    "org.jeongmo.migration.common.domain.base",
    "org.jeongmo.migration.common.domain.jpa",
    "org.jeongmo.migration.common.config",
])
class MemberApplication

fun main(args: Array<String>) {
    runApplication<MemberApplication>(*args)
}