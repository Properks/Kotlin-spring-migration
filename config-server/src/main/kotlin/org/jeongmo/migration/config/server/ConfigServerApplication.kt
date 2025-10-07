package org.jeongmo.migration.config.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer
import java.util.*


@SpringBootApplication
@EnableConfigServer
class ConfigServerApplication

fun main(args: Array<String>) {
    setPrivateKeyEnv()
    runApplication<ConfigServerApplication>(*args)
}

fun setPrivateKeyEnv() {

    val base64Key = System.getenv("CONFIG_REPOSITORY_PRIVATE_KEY") ?: return
    val privateKey= String(Base64.getDecoder().decode(base64Key))

    System.setProperty("CONFIG_REPOSITORY_PRIVATE_KEY", privateKey)
}