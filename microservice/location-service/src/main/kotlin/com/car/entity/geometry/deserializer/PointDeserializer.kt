package com.car.entity.geometry.deserializer

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import java.io.IOException


class PointDeserializer : JsonDeserializer<Point>() {
    private val factory = GeometryFactory()

    @Throws(IOException::class)
    override fun deserialize(jsonParser: JsonParser, ctxt: DeserializationContext?): Point? {
        val oc: ObjectCodec = jsonParser.getCodec()
        val node: JsonNode = oc.readTree(jsonParser)
        return point(node)
    }

    private fun point(node: JsonNode): Point? {
        var result: Point? = null
        val srid: String
        val coordinates = node["coordinates"] as ArrayNode
        val crsNode = node["crs"]
        if (coordinates != null) {
            result = point(coordinates)
        }
        if (crsNode != null) {
            val propertiesNode = crsNode["properties"]
            srid = propertiesNode["name"].textValue().replace("EPSG:", "")
            if (result == null) result = factory.createPoint(Coordinate(0.0, 0.0, 0.0))
            val intSrid = srid.toInt()
            result!!.srid = intSrid
        }
        return result
    }

    private fun point(coordinates: ArrayNode): Point? {
        val coordinate: Coordinate = toCoordinate(coordinates)
        return factory.createPoint(coordinate)
    }

    private fun toCoordinate(node: ArrayNode): Coordinate {
        var x = 0.0
        var y = 0.0
        var z = Double.NaN
        if (node.size() > 1) {
            x = node[0].asDouble()
            y = node[1].asDouble()
        }
        if (node.size() > 2) {
            z = node[2].asDouble()
        }
        return Coordinate(x, y, z)
    }
}