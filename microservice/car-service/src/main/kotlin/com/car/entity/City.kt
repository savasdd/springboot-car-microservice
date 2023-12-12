package com.car.entity

import jakarta.persistence.*
import lombok.*


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "City")
class City : BaseEntity() {

    @Column(name = "name")
    var name: String? = null

    @Column(name = "code", unique = true)
    var code: String? = null
}