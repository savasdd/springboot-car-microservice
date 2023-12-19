package com.car.event

import com.car.enums.EStockType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonTypeInfo
import lombok.*
import lombok.experimental.SuperBuilder
import java.math.BigDecimal

@Getter
@Setter
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@AllArgsConstructor
@NoArgsConstructor
@ToString
data class StockEvent(
    var id: String? = null,
    var carId: Long? = null,
    var stockId: Long? = null,
    var stockCount: Int? = null,
    var amount: BigDecimal? = null,
    var status: EStockType? = null,
    var carName: String? = null,
    var message: String? = null,
) {}
