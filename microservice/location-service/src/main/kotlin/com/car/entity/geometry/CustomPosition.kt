package com.car.entity.geometry

import lombok.Getter
import lombok.Setter
import java.sql.Date

@Getter
@Setter
class CustomPosition {
    private val Timestamp: Date? = null
    private val Latitude = 0.0
    private val Longitude = 0.0
    private val Altitude = 0.0
    private val Accuracy = 0.0
    private val AltitudeAccuracy = 0.0
    private val Heading = 0.0
    private val Speed = 0.0
}