package org.jeongmo.practice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class PracticeApplication

fun main(args: Array<String>) {
    runApplication<PracticeApplication>(*args)
}
