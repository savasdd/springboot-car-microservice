package com.car.entity

import com.car.entity.base.BaseEntity
import com.car.entity.geometry.deserializer.GeometryDeserializer
import com.car.entity.geometry.serializer.GeometrySerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.locationtech.jts.geom.Geometry

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Location")
data class Location(
    var name: String? = null,
    var carId: Long? = null,

    @JsonSerialize(using = GeometrySerializer::class)
    @JsonDeserialize(using = GeometryDeserializer::class)
    @Column(name = "geometry")
    var geom: Geometry? = null

) : BaseEntity() {}
