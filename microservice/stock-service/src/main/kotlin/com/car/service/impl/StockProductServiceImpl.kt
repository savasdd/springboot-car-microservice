package com.car.service.impl

import com.car.dto.ResponseData
import com.car.entity.StockProduct
import com.car.exception.GeneralException
import com.car.exception.GeneralWarning
import com.car.repository.StockProductRepository
import com.car.repository.StockRepository
import com.car.service.StockProductService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.webjars.NotFoundException

@Service
class StockProductServiceImpl(
    private val repository: StockProductRepository,
    private val stockRepository: StockRepository

) : StockProductService {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Throws(GeneralException::class, GeneralWarning::class)
    override fun getAll(): ResponseData<StockProduct> {
        val list = repository.findAll()
        log.info("get all StockProduct")

        return ResponseData(items = list, totalCount = list.size)
    }

    @Throws(GeneralException::class, GeneralWarning::class)
    override fun getOne(id: Long): StockProduct {
        return repository.findById(id).orElseThrow { NotFoundException("Not Found!") }
    }

    @Throws(GeneralException::class, GeneralWarning::class)
    override fun create(dto: StockProduct): StockProduct {
        val stock = dto.stock?.id?.let { stockRepository.findById(it) }
        dto.version = 0L
        dto.stock = stock?.get()
        val module = repository.save(dto)

        log.info("create StockProduct")
        return module
    }

    @Throws(GeneralException::class, GeneralWarning::class)
    override fun update(id: Long, dto: StockProduct): StockProduct {
        val stock = dto.stock?.id?.let { stockRepository.findById(it) }
        val stocks = repository.findById(id)

        val update = stocks.stream().map { m ->
            m.stock = stock?.get()
            m.carId = dto.carId
            m.description = dto.description
            return@map m
        }.findFirst().get()

        val model = repository.save(update)
        log.info("update StockProduct")
        return model
    }

    @Throws(GeneralException::class, GeneralWarning::class)
    override fun delete(id: Long) {
        if (repository.existsById(id))
            repository.deleteById(id)
    }
}