package com.car.entity

import com.car.entity.base.BaseEntity
import com.car.enums.*
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.hibernate.annotations.Where
import java.math.BigDecimal
import java.util.*
import kotlin.jvm.Transient

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Car")
@Where(clause = "is_deleted = 0")
data class Car(

    var brand: String? = null,
    var model: String? = null,
    var advert: String? = null,
    var year: Int? = null,
    var km: BigDecimal? = null,
    var price: BigDecimal? = null,
    var advertDate: Date? = null,

    @Enumerated(EnumType.STRING)
    var color: EColorType? = null,
    @Enumerated(EnumType.STRING)
    var damage: EDamageType? = null,
    @Enumerated(EnumType.STRING)
    var engine: EEngineType? = null,
    @Enumerated(EnumType.STRING)
    var fuel: EFuelType? = null,
    @Enumerated(EnumType.STRING)
    var gear: EGearType? = null,
    @Enumerated(EnumType.STRING)
    var guarantee: EGuaranteeType? = null,
    @Enumerated(EnumType.STRING)
    var plate: EPlateType? = null,
    @Enumerated(EnumType.STRING)
    var state: EStateType? = null,
    @Enumerated(EnumType.STRING)
    var swap: ESwapType? = null,
    @Enumerated(EnumType.STRING)
    var traction: ETractionType? = null,
    @Enumerated(EnumType.STRING)
    var who: EWhoType? = null,
    @Enumerated(EnumType.STRING)
    var video: EVideoType? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    var city: City? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var town: Town? = null,

    @Transient
    var image: String? = null,

    ) : BaseEntity() {}
