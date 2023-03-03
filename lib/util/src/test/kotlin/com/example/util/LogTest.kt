package com.example.util

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.forAll

class LogTest : FunSpec({

    context("ROOT_LOGGER") {
        test("returns the logger named 'ROOT'") {
            ROOT_LOGGER.name shouldBe "ROOT"
        }
    }

    context("logger(name)") {
        test("returns the logger with the given name") {
            forAll<String>(10) { name ->
                logger(name).name == name
            }
        }
    }

    fun lastName(name: String): String = name.split(".").last()

    context("logger(clazz)") {

        test("returns the logger with the name of the given non-companion class") {
            lastName(SomeClass1().log.name) shouldBe "SomeClass1"
        }

        test("returns the logger with the name of the enclosing class of the given companion class") {
            lastName(SomeClass1.log.name) shouldBe "SomeClass1"
        }
    }

    context("logger()") {

        test("returns the logger with the name of the given non-companion class") {
            lastName(SomeClass2().log.name) shouldBe "SomeClass2"
        }

        test("returns the logger with the name of the enclosing class of the given companion class") {
            lastName(SomeClass2.log.name) shouldBe "SomeClass2"
        }

        test("returns the logger with the name of the parent class") {
            lastName((object : SomeClass2() {}).log.name) shouldBe "SomeClass2"
        }
    }
})

class SomeClass1 {
    val log = logger(this::class)

    companion object {
        val log = logger(this::class)
    }
}

open class SomeClass2 {
    val log = logger()

    companion object {
        val log = logger()
    }
}
