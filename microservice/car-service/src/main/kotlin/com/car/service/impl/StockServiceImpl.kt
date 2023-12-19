package com.car.service.impl

import com.car.enums.EStockType
import com.car.event.StockEvent
import com.car.repository.CarRepository
import com.car.service.StockService
import com.car.utils.JsonUtil
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.webjars.NotFoundException
import java.util.*


@Service
class StockServiceImpl(
    private val repository: CarRepository,
    private val template: KafkaTemplate<String, String>

) : StockService {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val topic = "stock"
    private val topicNotification = "stock_notification"
    override fun create(event: StockEvent): StockEvent {
        val car = event.carId?.let { repository.findById(it).orElseThrow { NotFoundException("Car Not Found!") } }

        event.id = UUID.randomUUID().toString()
        event.carId = car?.id
        event.carName = car?.model
        event.message = car?.brand + ", " + car?.model + " eklendi"
        val json: String = JsonUtil.toJson(event)

        template.send(topic, event.id.toString(), json)
        log.info("Sent: {}", event)
        return event
    }


    override fun confirm(event: StockEvent): StockEvent {
        val order = StockEvent(
            id = event.id,
            carId = event.carId,
            stockId = event.stockId,
            stockCount = event.stockCount,
            amount = event.amount
        )

        when (event.status) {
            EStockType.ACCEPT -> order.status = EStockType.CONFIRMED
            EStockType.REJECT -> order.status = EStockType.REJECT

            else -> order.status = EStockType.ROLLBACK
        }

        return order
    }

}