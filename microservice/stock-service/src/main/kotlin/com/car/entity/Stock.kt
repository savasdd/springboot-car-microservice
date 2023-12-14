package com.car.entity

import com.car.entity.base.BaseEntity
import com.car.enums.EStockType
import com.car.enums.EUnitType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.util.Date

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Stock")
data class Stock(
    var name: String? = null,
    var availableItems: Int? = 0,
    var reservedItems: Int? = 0,
    var transactionDate: Date? = null,

    @Enumerated(EnumType.STRING)
    var unit: EUnitType? = null,
    @Enumerated(EnumType.STRING)
    var stockType: EStockType? = null,

    ) : BaseEntity() {}
