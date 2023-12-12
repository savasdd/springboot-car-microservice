package com.car.repository

import com.car.entity.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

//@Repository
interface CityRepository : JpaRepository<City, Long> {}