package com.car.service.impl

import com.car.entity.Town
import com.car.repository.CityRepository
import com.car.repository.TownRepository
import com.car.service.TownService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.webjars.NotFoundException

@Service
class TownServiceImpl(
    var repository: TownRepository,
    var cityRepository: CityRepository
) : TownService {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun getAll(): List<Town> {
        val list = repository.findAll();
        log.info("get all town {} ", list.size)
        return list
    }

    override fun getOne(id: Long): Town {
        return repository.findById(id).orElseThrow { NotFoundException("Not found!") }
    }

    override fun create(dto: Town): Town {
        if (dto.city == null)
            throw Exception("City is required!")

        val city = dto.city?.id?.let { cityRepository.findById(it) }
        dto.version = 0L
        dto.city = city?.get()

        val model = repository.save(dto)
        log.info("create town")
        return model
    }

    override fun update(id: Long, dto: Town): Town {
        val city = dto.city?.id?.let { cityRepository.findById(it) }
        val towns = repository.findById(id)

        val update = towns.stream().map { m ->
            m.name = dto?.name
            m.code = dto?.code
            m.city = city?.get()
            return@map m
        }.findFirst().get()

        val model = repository.save(update)
        log.info("update town")
        return model
    }

    override fun delete(id: Long) {
        if (repository.existsById(id))
            repository.deleteById(id)
    }
}