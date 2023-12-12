package com.car.service

import com.car.entity.Town

interface TownService {
    fun getAll(): List<Town>
    fun getOne(id: Long): Town
    fun create(dto: Town): Town
    fun update(id: Long, dto: Town): Town
    fun delete(id: Long)
}