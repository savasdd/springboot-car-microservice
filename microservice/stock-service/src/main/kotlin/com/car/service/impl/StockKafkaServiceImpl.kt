package com.car.service.impl

import com.car.enums.EStockType
import com.car.event.StockEvent
import com.car.repository.StockProductRepository
import com.car.service.StockKafkaService
import com.car.utils.JsonUtil
import org.apache.commons.lang3.event.EventUtils
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.webjars.NotFoundException
import java.util.*


@Service
class StockKafkaServiceImpl(
    private val repository: StockProductRepository,
    private val template: KafkaTemplate<String, String>

) : StockKafkaService {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val topic = "stock"
    private val topicNotification = "stock_notification"
    override fun create(event: StockEvent): StockEvent {
        event.id = UUID.randomUUID().toString()
        event.message = "ürün eklendi"
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

    override fun confirmStock(event: StockEvent?) {
        val product = repository.findByCarIdAndStock_Id(event?.carId, event?.stockId)
            .orElseThrow { NotFoundException("Product not fund!") }
        log.info("Found: {}", event?.carName)

        when (event?.status) {
            EStockType.ACCEPT -> {
                event.status = EStockType.ACCEPT
                product.stock?.stockType = EStockType.ACCEPT
            }

            EStockType.ROLLBACK -> {
                event.status = EStockType.ROLLBACK
                product.stock?.stockType = EStockType.ROLLBACK
            }

            else -> log.error("product not found")
        }

        event?.carName = product.name
        event?.message = "ürün stoktan düşürüldü"
        val json = JsonUtil.toJson(event)

        repository.save(product)
        template.send(topicNotification, event?.id.toString(), json)
        log.info("Sent: {}", event)
    }
}