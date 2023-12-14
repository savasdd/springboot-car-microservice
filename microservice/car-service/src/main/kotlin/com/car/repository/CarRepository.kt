package com.car.repository

import com.car.entity.Car
import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository : JpaRepository<Car, Long> {
}