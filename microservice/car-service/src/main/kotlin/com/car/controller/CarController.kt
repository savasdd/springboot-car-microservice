package com.car.controller

import com.car.dto.ResponseData
import com.car.entity.Car
import com.car.service.CarService
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
@RequestMapping("/api/cars")
class CarController(
    private val service: CarService
) {

    @GetMapping(value = ["/getAll"])
    fun getAllCar(): ResponseEntity<ResponseData<Car>> {
        return ResponseEntity.ok(service.getAll())
    }

    @GetMapping(value = ["/getOne/{id}"])
    fun getOneCar(@PathVariable id: Long): ResponseEntity<Car> {
        return ResponseEntity.ok(service.getOne(id))
    }

    @PostMapping(value = ["/save"])
    fun createCar(@RequestBody dto: Car): ResponseEntity<Car> {
        return ResponseEntity(service.save(dto), HttpStatus.CREATED)
    }

    @PutMapping(value = ["/update/{id}"])
    fun updateCar(@PathVariable id: Long, @RequestBody dto: Car): ResponseEntity<Car> {
        return ResponseEntity.ok(service.update(id, dto))
    }

    @DeleteMapping(value = ["/delete/{id}"])
    fun deleteCar(@PathVariable id: Long): ResponseEntity<Car> {
        service.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}