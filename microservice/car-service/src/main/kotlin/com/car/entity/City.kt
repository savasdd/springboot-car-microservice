package com.car.entity

import com.car.entity.base.BaseEntity
import jakarta.persistence.*
import lombok.*


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "City")
data class City(
    @Column(name = "name")
    var name: String? = null,
    @Column(name = "code", unique = true)
    var code: String? = null
) : BaseEntity() {}