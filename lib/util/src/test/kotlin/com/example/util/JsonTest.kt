package com.example.util

import com.fasterxml.jackson.core.JsonProcessingException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import io.kotest.property.Exhaustive
import io.kotest.property.exhaustive.boolean
import io.kotest.property.forAll
import java.math.BigDecimal
import java.math.BigInteger

class JsonTest : FunSpec({

    context("Any.toJson") {
        context("Converts anything that can be processed by Jackson to its json string") {
            val testCases = listOf(
                true to "true",
                false to "false",
                0 to "0",
                0.00 to "0.0",
                BigInteger.TEN to "10",
                BigDecimal.TEN to "10",
                "" to "\"\"",
                "{}" to "\"{}\"",
                emptyList<Nothing>() to "[]",
                listOf("a", "b") to """["a","b"]""",
                emptyMap<Nothing, Nothing>() to "{}",
                mapOf("a" to "A", "b" to "B") to """{"a":"A","b":"B"}""",
            )

            withData<Pair<Any, String>>(
                { (obj, expected) -> "invoking toJson on ${obj::class.simpleName} $obj yields `$expected`" },
                testCases,
            ) { (obj, expected) ->
                obj.toJson() shouldBe expected
            }
        }

        context("Converts data class to its json string") {
            val testCases = listOf(
                SomeData(1, null) to """{"id":1,"name":null,"friendIds":[]}""",
                SomeData(2, "foo") to """{"id":2,"name":"foo","friendIds":[]}""",
                SomeData(3, "foo", listOf(10)) to """{"id":3,"name":"foo","friendIds":[10]}""",
                SomeData(4, "foo", listOf(1, 10)) to """{"id":4,"name":"foo","friendIds":[1,10]}""",
            )

            withData<Pair<Any, String>>(
                { (obj, expected) -> "invoking toJson on $obj yields `$expected`" },
                testCases,
            ) { (obj, expected) ->
                obj.toJson() shouldBe expected
            }
        }
    }

    context("String.toJsonObject") {
        test("Converts a valid json list string to a list") {
            val list = """["a", "b", "c"]""".toJsonObject<List<String>>()
            list shouldBe listOf("a", "b", "c")
        }

        test("Converts a valid map json string to a map") {
            val map = """{"a": "b", "c": "d"}""".toJsonObject<Map<String, String>>()
            map shouldBe mapOf("a" to "b", "c" to "d")
        }

        test("Converts a valid object json string to a data object") {
            val data = """{"id":1,"name":"foo","friendIds":[10]}""".toJsonObject<SomeData>()
            data shouldBe SomeData(1, "foo", listOf(10))
        }

        test("Converts a valid object json string to a data object with default values") {
            val data = """{"id":1}""".toJsonObject<SomeData>()
            data shouldBe SomeData(1, null)
        }

        test("Converts a valid object json string to a data object and ignores unknown values") {
            val data = """{"id":1,"name":null,"unknown":"unknown"}""".toJsonObject<SomeData>()
            data shouldBe SomeData(1, null)
        }

        test("Throws exceptions if the json string is not valid") {
            shouldThrow<JsonProcessingException> {
                "{\"foo\"".toJsonObject<Map<String, String>>()
            }
        }

        test("Throws exceptions if the json string does not match the target type") {
            shouldThrow<JsonProcessingException> {
                "{\"foo\",\"bar\"}".toJsonObject<List<String>>()
            }
        }
    }

    context("Converts to json and back") {
        val iterations = 10

        test("Converting an object to json string and back yields an object that equals the original one") {
            forAll(Exhaustive.boolean()) { it.toJson().toJsonObject<Boolean>() == it }
            forAll<Int>(iterations) { it.toJson().toJsonObject<Int>() == it }
            forAll<String>(iterations) { it.toJson().toJsonObject<String>() == it }
            forAll<List<Int>>(iterations) { it.toJson().toJsonObject<List<Int>>() == it }
            forAll<List<String>>(iterations) { it.toJson().toJsonObject<List<String>>() == it }
            forAll<Map<String, String>>(iterations) { it.toJson().toJsonObject<Map<String, String>>() == it }
            forAll<SomeData>(iterations) { it.toJson().toJsonObject<SomeData>() == it }
        }

        test("Converting an object to json node and back yields an object that equals the original one") {
            forAll(Exhaustive.boolean()) { it.toJson().toJsonNode().toObject<Boolean>() == it }
            forAll<Int>(iterations) { it.toJson().toJsonNode().toObject<Int>() == it }
            forAll<String>(iterations) { it.toJson().toJsonNode().toObject<String>() == it }
            forAll<List<Int>>(iterations) { it.toJson().toJsonNode().toObject<List<Int>>() == it }
            forAll<List<String>>(iterations) { it.toJson().toJsonNode().toObject<List<String>>() == it }
            forAll<Map<String, String>>(iterations) { it.toJson().toJsonNode().toObject<Map<String, String>>() == it }
            forAll<SomeData>(iterations) { it.toJson().toJsonNode().toObject<SomeData>() == it }
        }
    }
})

data class SomeData(
    val id: Int,
    val name: String?,
    val friendIds: List<Int> = emptyList(),
)
