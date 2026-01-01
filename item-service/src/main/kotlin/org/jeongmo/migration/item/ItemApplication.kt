package org.jeongmo.migration.item

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "org.jeongmo.migration.item",
        "org.jeongmo.migration.common.config.api.payload",
        "org.jeongmo.migration.common.domain.jpa",
        "org.jeongmo.migration.common.utils.retry",
    ]
)
class ItemApplication

fun main(args: Array<String>) {
    runApplication<ItemApplication>(*args)
}