package org.jeongmo.migration

import io.github.cdimascio.dotenv.Dotenv
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
    val dotenv = Dotenv.configure()
        .directory("./")
        .load()

    val base64Key = dotenv["CONFIG_REPOSITORY_PRIVATE_KEY"]
    val privateKey= String(Base64.getDecoder().decode(base64Key))

    System.setProperty("CONFIG_REPOSITORY_PRIVATE_KEY", privateKey)
}