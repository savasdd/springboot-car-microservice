package com.car.entity.geometry.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Point
import java.io.IOException

class PointSerializer : JsonSerializer<Point>() {

    @Throws(IOException::class)
    override fun serialize(point: Point?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        if (point != null && gen != null) {
            write(point, gen)
        }
    }

    @Throws(IOException::class)
    private fun write(point: Point, gen: JsonGenerator) {
        gen.writeStartObject()
        gen.writeStringField("type", "Point")
        gen.writeFieldName("coordinates")
        writeCoordinate(point.coordinate, gen)
        gen.writeFieldName("crs")
        writeCrs(point, gen)
        gen.writeEndObject()
    }

    @Throws(IOException::class)
    private fun writeCoordinate(coordinate: Coordinate, gen: JsonGenerator) {
        gen.writeStartArray()
        gen.writeNumber(coordinate.x)
        gen.writeNumber(coordinate.y)
        if (!java.lang.Double.isNaN(coordinate.z)) {
            gen.writeNumber(coordinate.z)
        }
        gen.writeEndArray()
    }

    @Throws(IOException::class)
    private fun writeCrs(point: Point, gen: JsonGenerator) {
        gen.writeStartObject()
        gen.writeStringField("type", "name")
        gen.writeFieldName("properties")
        writeProperties(point.srid, gen)
        gen.writeEndObject()
    }

    @Throws(IOException::class)
    private fun writeProperties(srid: Int, gen: JsonGenerator) {
        gen.writeStartObject()
        gen.writeStringField("name", "EPSG:$srid")
        gen.writeEndObject()
    }
}