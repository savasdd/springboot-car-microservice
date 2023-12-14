package com.car.entity

import com.car.entity.base.BaseEntity
import jakarta.persistence.*
import lombok.*

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Town")
data class Town(
    @Column(name = "name")
    var name: String? = null,

    @Column(name = "code")
    var code: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    var city: City? = null

) : BaseEntity() {}