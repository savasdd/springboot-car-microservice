package com.car.repository

import com.car.entity.Stock
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository:JpaRepository<Stock,Long> {
}