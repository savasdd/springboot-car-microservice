package com.car.entity.base

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import java.util.*


@Getter
@Setter
@MappedSuperclass
open class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @JsonIgnore
    @Column(name = "createdDate", updatable = false)
    private var createdDate: Date? = null

    @JsonIgnore
    @Column(name = "updatedDate", updatable = false)
    private var updatedDate: Date? = null

    @JsonIgnore
    @Column(name = "isDeleted", nullable = false)
    private var isDeleted: Long? = null

    @JsonIgnore
    @CreatedBy
    @Column(name = "createdBy")
    private var createdBy: String? = null

    @JsonIgnore
    @LastModifiedBy
    @Column(name = "updatedBy")
    private var updatedBy: String? = null

    @JsonIgnore
    @Version
    var version: Long? = null

    @PreUpdate
    fun setPreUpdate() {
        updatedDate = Date()
    }

    @PrePersist
    fun setPrePersist() {
        createdDate = Date()
        this.isDeleted = 0
    }

}