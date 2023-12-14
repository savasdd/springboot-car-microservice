package com.car.entity

import com.car.entity.base.BaseEntity
import jakarta.persistence.*
import lombok.*
import org.hibernate.annotations.Where


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "City")
@Where(clause = "is_deleted = 0")
data class City(
    @Column(name = "name")
    var name: String? = null,
    @Column(name = "code", unique = true)
    var code: String? = null
) : BaseEntity() {}