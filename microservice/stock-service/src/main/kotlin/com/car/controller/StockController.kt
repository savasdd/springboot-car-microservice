package com.car.controller

import com.car.dto.ResponseData
import com.car.entity.Stock
import com.car.service.StockService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/stocks")
class StockController(
    private val service: StockService,

    ) {

    @GetMapping(value = ["/getAll"])
    fun getAllStock(): ResponseEntity<ResponseData<Stock>> {
        return ResponseEntity.ok(service.getAll())
    }

    @GetMapping(value = ["/getOne/{id}"])
    fun getOneStock(@PathVariable id: Long): ResponseEntity<Stock> {
        return ResponseEntity.ok(service.getOne(id))
    }

    @PostMapping(value = ["/save"])
    fun createStock(@RequestBody dto: Stock): ResponseEntity<Stock> {
        return ResponseEntity(service.create(dto), HttpStatus.CREATED)
    }

    @PutMapping(value = ["/update/{id}"])
    fun updateStock(@PathVariable id: Long, @RequestBody dto: Stock): ResponseEntity<Stock> {
        return ResponseEntity.ok(service.update(id, dto))
    }

    @DeleteMapping(value = ["/delete/{id}"])
    fun deleteStock(@PathVariable id: Long): ResponseEntity<Stock> {
        service.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}