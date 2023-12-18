package com.car

import com.car.service.CityService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.core.io.ClassPathResource
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.scheduling.annotation.EnableAsync


@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
@EnableAsync
class CarApplication(
    private val service: CityService
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        saveAllCity(service)
    }
}

fun main(args: Array<String>) {
    runApplication<CarApplication>(*args)
}

fun saveAllCity(service: CityService) {
    val resource = ClassPathResource("file/city.txt")
    val inputStream = resource.inputStream
    inputStream.close()


}