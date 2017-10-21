package io.github.prsaver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class PrsaverApplication

fun main(args: Array<String>) {
    SpringApplication.run(PrsaverApplication::class.java, *args)
}
