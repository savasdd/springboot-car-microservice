package com.car

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class CarApplication

fun main(args: Array<String>) {
    runApplication<CarApplication>(*args)
}