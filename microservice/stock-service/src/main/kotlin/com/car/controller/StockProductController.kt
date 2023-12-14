package com.car.controller

import com.car.dto.ResponseData
import com.car.entity.StockProduct
import com.car.service.StockProductService
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
@RequestMapping("/api/stocks/product")
class StockProductController(
    private val service: StockProductService,
) {

    @GetMapping(value = ["/getAll"])
    fun getAllStockProduct(): ResponseEntity<ResponseData<StockProduct>> {
        return ResponseEntity.ok(service.getAll())
    }

    @GetMapping(value = ["/getOne/{id}"])
    fun getOneStockProduct(@PathVariable id: Long): ResponseEntity<StockProduct> {
        return ResponseEntity.ok(service.getOne(id))
    }

    @PostMapping(value = ["/save"])
    fun createStockProduct(@RequestBody dto: StockProduct): ResponseEntity<StockProduct> {
        return ResponseEntity(service.create(dto), HttpStatus.CREATED)
    }

    @PutMapping(value = ["/update/{id}"])
    fun updateStockProduct(@PathVariable id: Long, @RequestBody dto: StockProduct): ResponseEntity<StockProduct> {
        return ResponseEntity.ok(service.update(id, dto))
    }

    @DeleteMapping(value = ["/delete/{id}"])
    fun deleteStockProduct(@PathVariable id: Long): ResponseEntity<StockProduct> {
        service.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}