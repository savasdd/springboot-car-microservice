package com.car.service

import com.car.dto.ResponseData
import com.car.entity.StockProduct

interface StockProductService {
    fun getAll(): ResponseData<StockProduct>
    fun getOne(id: Long): StockProduct
    fun create(dto: StockProduct): StockProduct
    fun update(id: Long, dto: StockProduct): StockProduct
    fun delete(id: Long)
}