package com.car.entity.geometry.deserializer

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.locationtech.jts.geom.*;

import java.io.IOException;


class GeometryDeserializer : JsonDeserializer<Geometry>() {
    private val factory = GeometryFactory()

    @Throws(IOException::class)
    override fun deserialize(jsonParser: JsonParser, arg1: DeserializationContext?): Geometry? {
        val oc: ObjectCodec = jsonParser.getCodec()
        val node: JsonNode = oc.readTree(jsonParser)
        return geometry(node)
    }

    private fun geometry(node: JsonNode): Geometry? {
        var result: Geometry? = null
        val type = node["type"].textValue()
        val coordinates = node["coordinates"] as ArrayNode

        when (type) {
            "Point" -> result = point(coordinates)
            "MultiPoint" -> result = multiPoint(coordinates)
            "LineString" -> result = lineString(coordinates)
            "MultiLineString" -> result = multiLineString(coordinates)
            "Polygon" -> result = polygon(coordinates)
            "MultiPolygon" -> result = multiPolygon(coordinates)
            "GeometryCollection" -> result = geometryCollection(node["geometries"] as ArrayNode)
            "FeatureCollection" -> result = featureCollection(node["features"] as ArrayNode)
        }

        assert(result != null)
        val crsNode = node["crs"]
        if (crsNode != null) {
            val propertiesNode = crsNode["properties"]
            val srid = propertiesNode["name"].textValue().replace("EPSG:", "")
            val intSrid = srid.toInt()
            result!!.srid = intSrid
        } else {
            result!!.srid = 4326
        }
        return result
    }

    private fun point(coordinates: ArrayNode): Geometry? {
        val coordinate: Coordinate = toCoordinate(coordinates)
        return factory.createPoint(coordinate)
    }

    private fun multiPoint(nodes: ArrayNode): Geometry? {
        val coordinates: Array<Coordinate?> = toCoordinateArray(nodes)
        return factory.createMultiPoint(coordinates)
    }

    private fun lineString(nodes: ArrayNode): LineString? {
        val coordinates: Array<Coordinate?> = toCoordinateArray(nodes)
        return factory.createLineString(coordinates)
    }

    private fun multiLineString(nodes: ArrayNode): MultiLineString? {
        val lineStrings = arrayOfNulls<LineString>(nodes.size())
        for (i in lineStrings.indices) {
            lineStrings[i] = lineString(nodes[i] as ArrayNode)
        }
        return factory.createMultiLineString(lineStrings)
    }

    private fun polygon(nodes: ArrayNode): Polygon? {
        val outerRing: LinearRing = toLinearRing(nodes[0] as ArrayNode)
        val innerRings = arrayOfNulls<LinearRing>(nodes.size() - 1)
        for (i in innerRings.indices) {
            innerRings[i] = toLinearRing(nodes[i + 1] as ArrayNode)
        }
        return factory.createPolygon(outerRing, innerRings)
    }

    private fun multiPolygon(nodes: ArrayNode): MultiPolygon? {
        val polygons = arrayOfNulls<Polygon>(nodes.size())
        for (i in polygons.indices) {
            polygons[i] = polygon(nodes[i] as ArrayNode)
        }
        return factory.createMultiPolygon(polygons)
    }

    private fun geometryCollection(nodes: ArrayNode): GeometryCollection? {
        val geometries = arrayOfNulls<Geometry>(nodes.size())
        var ii = 0
        for (i in geometries.indices) {
            if (!nodes[i].isNull) {
                geometries[i] = geometry(nodes[i])
                ii++
            }
        }
        val geoms = arrayOfNulls<Geometry>(ii)
        var it = 0
        for (geometry in geometries) {
            if (geometry != null) {
                geoms[it] = geometry
                it++
            }
        }
        return factory.createGeometryCollection(geoms)
    }

    private fun featureCollection(nodes: ArrayNode): GeometryCollection? {
        val geometries = arrayOfNulls<Geometry>(nodes.size())
        for (i in geometries.indices) {
            geometries[i] = geometry(nodes[i]["geometry"])
        }
        return factory.createGeometryCollection(geometries)
    }

    private fun toLinearRing(nodes: ArrayNode): LinearRing {
        val coordinates = toCoordinateArray(nodes)
        return factory.createLinearRing(coordinates)
    }

    private fun toCoordinateArray(nodes: ArrayNode): Array<Coordinate?> {
        val result = arrayOfNulls<Coordinate>(nodes.size())
        for (i in result.indices) {
            result[i] = toCoordinate(nodes[i] as ArrayNode)
        }
        return result
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