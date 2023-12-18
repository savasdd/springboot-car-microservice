package com.car

import com.car.event.StockEvent
import com.car.service.StockKafkaService
import com.car.utils.JsonUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.scheduling.annotation.EnableAsync


@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
class StockApplication

private val log = LoggerFactory.getLogger(StockApplication::class.java)


fun main(args: Array<String>) {
    runApplication<StockApplication>(*args)
}


@Autowired
private val service: StockKafkaService? = null

@KafkaListener(id = "orders", topics = ["stock"], groupId = "stock_kafka")
fun onEvent(order: String?) {

    log.info("Received: {}", order)
    val event: StockEvent? = JsonUtil.fromJson(order, StockEvent::class.java)
    service?.confirmStock(event)
}

