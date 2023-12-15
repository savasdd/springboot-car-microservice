package com.car.service.impl

import com.car.dto.ResponseData
import com.car.entity.City
import com.car.exception.GeneralException
import com.car.repository.CityRepository
import com.car.service.CityService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.webjars.NotFoundException

@Service
class CityServiceImpl(
    private val repository: CityRepository
) : CityService {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Throws(GeneralException::class)
    override fun findAll(): ResponseData<City> {
        val list = repository.findAll();

        log.info("get all car {}", list.size)
        return ResponseData(items = list, totalCount = list.size);
    }

    @Throws(GeneralException::class)
    override fun findOne(id: Long): City {
        return repository.findById(id).orElseThrow { NotFoundException("Not found!") }
    }

    @Throws(GeneralException::class)
    override fun create(dto: City): City {
        dto.version = 0L
        val model = repository.save(dto)

        log.info("create car {}", model.id)
        return model
    }

    @Throws(GeneralException::class)
    override fun update(id: Long, dto: City): City {
        val cars = repository.findById(id)
        val update = cars.stream().map { m ->
            m.name = dto?.name
            m.code = dto?.code
            return@map m
        }.findFirst().get();

        val model = repository.save(update)
        log.info("update car {}", id)
        return model
    }

    @Throws(GeneralException::class)
    override fun delete(id: Long) {
        if (repository.existsById(id))
            repository.deleteById(id)
    }
}