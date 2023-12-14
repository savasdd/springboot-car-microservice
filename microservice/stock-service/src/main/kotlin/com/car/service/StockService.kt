package com.car.service

import com.car.dto.ResponseData
import com.car.entity.Stock

interface StockService {
    fun getAll(): ResponseData<Stock>
    fun getOne(id: Long): Stock
    fun create(dto: Stock): Stock
    fun update(id: Long, dto: Stock): Stock
    fun delete(id: Long)
}