package com.car

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
class StockApplication

private val log = LoggerFactory.getLogger(StockApplication::class.java)

fun main(args: Array<String>) {
    runApplication<StockApplication>(*args)
}


