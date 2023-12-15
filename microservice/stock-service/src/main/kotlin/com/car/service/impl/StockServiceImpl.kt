package com.car.service.impl

import com.car.dto.ResponseData
import com.car.entity.Stock
import com.car.exception.GeneralException
import com.car.exception.GeneralWarning
import com.car.repository.StockRepository
import com.car.service.StockService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.webjars.NotFoundException

@Service
class StockServiceImpl(
    private val repository: StockRepository,

    ) : StockService {
    private val log = LoggerFactory.getLogger(this::class.java)

    @Throws(GeneralException::class, GeneralWarning::class)
    override fun getAll(): ResponseData<Stock> {
        val list = repository.findAll()
        log.info("get all Stock")

        return ResponseData(items = list, totalCount = list.size)
    }

    @Throws(GeneralException::class, GeneralWarning::class)
    override fun getOne(id: Long): Stock {
        return repository.findById(id).orElseThrow { NotFoundException("Not Found!") }
    }


    @Throws(GeneralException::class, GeneralWarning::class)
    override fun create(dto: Stock): Stock {
        dto.version = 0L
        val model = repository.save(dto)

        log.info("create Stock")
        return model
    }

    @Throws(GeneralException::class, GeneralWarning::class)
    override fun update(id: Long, dto: Stock): Stock {
        val stocks = repository.findById(id)

        val update = stocks.stream().map { m ->
            m.name = dto?.name
            m.availableItems = dto?.availableItems
            m.reservedItems = dto?.reservedItems
            m.transactionDate = dto?.transactionDate
            m.unit = dto?.unit
            m.stockType = dto?.stockType
            return@map m
        }.findFirst().get()

        val model = repository.save(update)
        log.info("update Stock")
        return model
    }

    @Throws(GeneralException::class, GeneralWarning::class)
    override fun delete(id: Long) {
        if (repository.existsById(id))
            repository.deleteById(id)
    }
}