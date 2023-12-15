package com.car.entity.geometry.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.locationtech.jts.geom.*
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode


class GeometrySerializer : JsonSerializer<Geometry>() {
    private var precision: Int? = null

    fun GeometrySerializer() {}

    fun GeometrySerializer(precision: Int?) {
        this.precision = precision
    }

    @Throws(IOException::class)
    override fun serialize(value: Geometry?, generator: JsonGenerator?, provider: SerializerProvider?) {
        if (value != null && generator != null) {
            writeGeometry(value, generator)
        }
    }

    @Throws(IOException::class)
    private fun writeGeometry(geom: Geometry, gen: JsonGenerator) {
        when (geom) {
            is Point -> write(geom as Point, gen)
            is MultiPoint -> write(geom as MultiPoint, gen)
            is LineString -> write(geom as LineString, gen)
            is MultiLineString -> write(geom as MultiLineString, gen)
            is Polygon -> write(geom as Polygon, gen)
            is MultiPolygon -> write(geom as MultiPolygon, gen)
            is GeometryCollection -> write(geom as GeometryCollection, gen)
            else -> throw RuntimeException("Unsupported Geometry type")
        }
    }

    @Throws(IOException::class)
    private fun write(point: Point, gen: JsonGenerator) {
        gen.writeStartObject()
        gen.writeStringField("type", "Point")
        gen.writeFieldName("coordinates")
        writeCoordinate(point.coordinate, gen)
        //gen.writeFieldName("crs");
        writeCrs(point.srid, gen)
        gen.writeEndObject()
    }

    @Throws(IOException::class)
    private fun write(points: MultiPoint, gen: JsonGenerator) {
        gen.writeStartObject()
        gen.writeStringField("type", "MultiPoint")
        gen.writeFieldName("coordinates")
        writeCoordinates(points.coordinates, gen)
        gen.writeEndObject()
    }

    @Throws(IOException::class)
    private fun write(geom: LineString, gen: JsonGenerator) {
        gen.writeStartObject()
        gen.writeStringField("type", "LineString")
        gen.writeFieldName("coordinates")
        writeCoordinates(geom.coordinates, gen)
        gen.writeEndObject()
    }

    @Throws(IOException::class)
    private fun write(geom: MultiLineString, gen: JsonGenerator) {
        gen.writeStartObject()
        gen.writeStringField("type", "MultiLineString")
        gen.writeFieldName("coordinates")
        gen.writeStartArray()
        for (i in 0 until geom.numGeometries) {
            writeCoordinates(geom.getGeometryN(i).coordinates, gen)
        }
        gen.writeEndArray()
        gen.writeFieldName("crs")
        writeCrs(geom.srid, gen)
        gen.writeEndObject()
    }

    @Throws(IOException::class)
    private fun write(coll: GeometryCollection, gen: JsonGenerator) {
        gen.writeStartObject()
        gen.writeStringField("type", "GeometryCollection")
        gen.writeArrayFieldStart("geometries")
        for (i in 0 until coll.numGeometries) {
            writeGeometry(coll.getGeometryN(i), gen)
        }
        gen.writeEndArray()
        writeCrs(coll.srid, gen)
        gen.writeEndObject()
    }

    @Throws(IOException::class)
    private fun write(geom: Polygon, gen: JsonGenerator) {
        gen.writeStartObject()
        gen.writeStringField("type", "Polygon")
        gen.writeFieldName("coordinates")
        gen.writeStartArray()
        writeCoordinates(geom.exteriorRing.coordinates, gen)
        for (i in 0 until geom.numInteriorRing) {
            writeCoordinates(geom.getInteriorRingN(i).coordinates, gen)
        }
        gen.writeEndArray()
        writeCrs(geom.srid, gen)
        gen.writeEndObject()
    }

    @Throws(IOException::class)
    private fun write(geom: MultiPolygon, gen: JsonGenerator) {
        gen.writeStartObject()
        gen.writeStringField("type", "MultiPolygon")
        gen.writeFieldName("coordinates")
        gen.writeStartArray()
        for (i in 0 until geom.numGeometries) {
            val p = geom.getGeometryN(i) as Polygon
            gen.writeStartArray()
            writeCoordinates(p.exteriorRing.coordinates, gen)
            for (j in 0 until p.numInteriorRing) {
                writeCoordinates(p.getInteriorRingN(j).coordinates, gen)
            }
            gen.writeEndArray()
        }
        gen.writeEndArray()
        writeCrs(geom.srid, gen)
        gen.writeEndObject()
    }

    @Throws(IOException::class)
    private fun writeCoordinate(coordinate: Coordinate, gen: JsonGenerator) {
        gen.writeStartArray()
        writeNumber(coordinate.getX(), gen)
        writeNumber(coordinate.getY(), gen)
        if (!java.lang.Double.isNaN(coordinate.getZ())) {
            writeNumber(coordinate.getZ(), gen)
        }
        gen.writeEndArray()
    }

    @Throws(IOException::class)
    private fun writeNumber(number: Double, gen: JsonGenerator) {
        if (precision != null) {
            gen.writeNumber(BigDecimal(number).setScale(precision!!, RoundingMode.HALF_UP))
        } else {
            gen.writeNumber(number)
        }
    }

    @Throws(IOException::class)
    private fun writeCoordinates(coordinates: Array<Coordinate>, gen: JsonGenerator) {
        gen.writeStartArray()
        for (coord in coordinates) {
            writeCoordinate(coord, gen)
        }
        gen.writeEndArray()
    }

    @Throws(IOException::class)
    private fun writeCrs(srid: Int, gen: JsonGenerator) {
        gen.writeFieldName("crs")
        gen.writeStartObject()
        gen.writeStringField("type", "name")
        gen.writeFieldName("properties")
        writeProperties(srid, gen)
        gen.writeEndObject()
    }

    @Throws(IOException::class)
    private fun writeProperties(srid: Int, gen: JsonGenerator) {
        gen.writeStartObject()
        gen.writeStringField("name", "EPSG:$srid")
        gen.writeEndObject()
    }

}