package com.car.controller

import com.car.entity.Town
import com.car.service.TownService
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
@RequestMapping("/api/cars/town")
class TownController(
    var service: TownService
) {

    @GetMapping(value = ["/getAll"])
    fun getAllTown(): ResponseEntity<List<Town>> {
        return ResponseEntity.ok(service.getAll())
    }

    @GetMapping(value = ["/getOne/{id}"])
    fun getOneTown(@PathVariable id: Long): ResponseEntity<Town> {
        return ResponseEntity.ok(service.getOne(id))
    }

    @PostMapping(value = ["/save"])
    fun createTown(@RequestBody dto: Town): ResponseEntity<Town> {
        return ResponseEntity(service.create(dto), HttpStatus.CREATED)
    }

    @PutMapping(value = ["/update/{id}"])
    fun updateTown(@PathVariable id: Long, @RequestBody dto: Town): ResponseEntity<Town> {
        return ResponseEntity.ok(service.update(id, dto))
    }

    @DeleteMapping(value = ["/delete/{id}"])
    fun deleteTown(@PathVariable id: Long): ResponseEntity<Town> {
        service.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}