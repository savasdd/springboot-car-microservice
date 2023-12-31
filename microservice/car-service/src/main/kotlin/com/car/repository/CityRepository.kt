package com.car.repository

import com.car.entity.City
import org.springframework.data.jpa.repository.JpaRepository

interface CityRepository : JpaRepository<City, Long> {}