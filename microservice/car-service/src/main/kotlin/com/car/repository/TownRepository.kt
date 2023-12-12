package com.car.repository

import com.car.entity.Town
import org.springframework.data.jpa.repository.JpaRepository

interface TownRepository : JpaRepository<Town, Long> {
}