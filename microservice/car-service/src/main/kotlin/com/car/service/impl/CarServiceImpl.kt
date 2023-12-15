package com.car.service.impl

import com.car.dto.ResponseData
import com.car.entity.Car
import com.car.exception.GeneralException
import com.car.repository.CarRepository
import com.car.repository.CityRepository
import com.car.repository.TownRepository
import com.car.service.CarService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.webjars.NotFoundException

@Service
class CarServiceImpl(
    private val repository: CarRepository,
    private val cityRepository: CityRepository,
    private val townRepository: TownRepository

) : CarService {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Throws(GeneralException::class)
    override fun getAll(): ResponseData<Car> {
        val list = repository.findAll()
        log.info("get all car")

        return ResponseData(items = list, totalCount = list.size)
    }

    @Throws(GeneralException::class)
    override fun getOne(id: Long): Car {
        return repository.findById(id).orElseThrow { NotFoundException("Not Found!") }
    }

    @Throws(GeneralException::class)
    override fun save(dto: Car): Car {
        val city = dto.city?.id?.let { cityRepository.findById(it) }
        val town = dto.town?.id?.let { townRepository.findById(it) }
        dto.version = 0L
        dto.city = city?.get()
        dto.town = town?.get()

        val model = repository.save(dto)
        log.info("create car")
        return model
    }

    @Throws(GeneralException::class)
    override fun update(id: Long, dto: Car): Car {
        val cars = repository.findById(id)
        val city = dto.city?.id?.let { cityRepository.findById(it) }
        val town = dto.town?.id?.let { townRepository.findById(it) }

        val update = cars.stream().map { m ->
            m.brand = dto?.brand
            m.model = dto?.model
            m.advert = dto?.advert
            m.year = dto?.year
            m.km = dto?.km
            m.price = dto?.price
            m.advertDate = dto?.advertDate
            m.color = dto?.color
            m.damage = dto?.damage
            m.engine = dto?.engine
            m.fuel = dto?.fuel
            m.gear = dto?.gear
            m.guarantee = dto?.guarantee
            m.plate = dto?.plate
            m.state = dto?.state
            m.swap = dto?.swap
            m.traction = dto?.traction
            m.video = dto?.video
            m.who = dto?.who

            m.city = city?.get()
            m.town = town?.get()
            return@map m
        }.findFirst().get()

        val model = repository.save(update)
        log.info("update car")
        return model
    }

    @Throws(GeneralException::class)
    override fun delete(id: Long) {
        if (repository.existsById(id))
            repository.deleteById(id)
    }
}