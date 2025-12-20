package org.jeongmo.migration.bought.item

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = [
    "org.jeongmo.migration.bought.item",
    "org.jeongmo.migration.common.config",
    "org.jeongmo.migration.common.domain.jpa",
    "org.jeongmo.migration.common.utils.retry",
])
class BoughtItemApplication

fun main(args: Array<String>) {
    runApplication<BoughtItemApplication>(*args)
}