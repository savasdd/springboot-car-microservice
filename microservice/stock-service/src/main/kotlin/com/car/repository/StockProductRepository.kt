package com.car.repository

import com.car.entity.StockProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StockProductRepository : JpaRepository<StockProduct, Long> {
    fun findByCarId(carId: Long): List<StockProduct>

    @Query("select max(v.stock.availableItems) from StockProduct v where v.isDeleted=0")
    fun getMaxAvailableItems(): Int
}