package com.car.controller

import com.car.service.CityService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cars")
class CityController(
    private val service: CityService
) {
}