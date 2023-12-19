package com.car.service

import com.car.event.StockEvent

interface StockService {
    fun create(event: StockEvent): StockEvent
    fun confirm(event: StockEvent): StockEvent

}