package com.car.controller

import com.car.dto.ResponseData
import com.car.entity.City
import com.car.service.CityService
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
@RequestMapping("/api/cars/city")
class CityController(
    private val service: CityService
) {

    @GetMapping(value = ["/getAll"])
    fun findAllCar(): ResponseEntity<ResponseData<City>> {
        return ResponseEntity.ok(service.findAll())
    }

    @GetMapping(value = ["/getOne/{id}"])
    fun findOneCar(@PathVariable id: Long): ResponseEntity<City> {
        return ResponseEntity.ok(service.findOne(id))
    }

    @PostMapping(value = ["/save"])
    fun createCar(@RequestBody dto: City): ResponseEntity<City> {
        return ResponseEntity(service.create(dto), HttpStatus.CREATED)
    }

    @PutMapping(value = ["/update/{id}"])
    fun updateCar(@PathVariable id: Long, @RequestBody dto: City): ResponseEntity<City> {
        return ResponseEntity.ok(service.update(id, dto))
    }

    @DeleteMapping(value = ["/delete/{id}"])
    fun deleteCar(@PathVariable id: Long): ResponseEntity<City> {
        service.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}