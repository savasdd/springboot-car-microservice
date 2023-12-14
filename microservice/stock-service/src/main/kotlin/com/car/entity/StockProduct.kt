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
import org.hibernate.annotations.Where

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "StockProduct")
@Where(clause = "is_deleted = 0")
data class StockProduct(
    var carId: Long? = null,
    var description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var stock: Stock? = null

) : BaseEntity() {}
