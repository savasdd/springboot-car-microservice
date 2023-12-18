package com.car.repository

import com.car.entity.StockProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface StockProductRepository : JpaRepository<StockProduct, Long> {
    fun findByCarId(carId: Long): List<StockProduct>
    fun findByCarIdAndStock_Id(carId: Long?, stockI: Long?): Optional<StockProduct>

    @Query("select max(v.stock.availableItems) from StockProduct v where v.isDeleted=0")
    fun getMaxAvailableItems(): Int
}