package com.car.entity

import com.car.entity.base.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "StockProduct")
data class StockProduct(
    val carId: Long? = null,
    val description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val stock: Stock? = null

) : BaseEntity() {}
