package com.car.service

import com.car.dto.ResponseData
import com.car.entity.City
import com.car.exception.GeneralException

interface CityService {

    fun findAll(): ResponseData<City>

    fun findOne(id: Long): City
    fun create(dto: City): City
    fun update(id: Long, dto: City): City
    fun delete(id: Long)
}