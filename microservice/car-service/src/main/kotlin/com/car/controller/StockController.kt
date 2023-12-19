package com.car.controller

import com.car.event.StockEvent
import com.car.service.StockService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cars/stock")
class StockController(
    private val service: StockService
) {

    @PostMapping(value = ["/save"])
    fun sendStock(@RequestBody event: StockEvent): ResponseEntity<StockEvent> {
        return ResponseEntity.ok(service.create(event))
    }
}