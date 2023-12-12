package com.car

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LocationApplication

fun main(args: Array<String>) {
    runApplication<LocationApplication>(*args)
}