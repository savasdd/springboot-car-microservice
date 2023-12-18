package com.car.service

import com.car.event.StockEvent

interface StockKafkaService {
    fun create(event: StockEvent): StockEvent
    fun confirm(event: StockEvent): StockEvent
    fun confirmStock(event: StockEvent?)

}