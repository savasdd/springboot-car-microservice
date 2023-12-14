package com.car.service

import com.car.dto.ResponseData
import com.car.entity.Car

interface CarService {
    fun getAll(): ResponseData<Car>
    fun getOne(id: Long): Car
    fun save(dto: Car): Car
    fun update(id: Long, dto: Car): Car
    fun delete(id: Long)
}