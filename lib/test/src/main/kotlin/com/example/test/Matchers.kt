package com.example.test

import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.matchers.date.shouldNotBeAfter
import io.kotest.matchers.date.shouldNotBeBefore
import io.kotest.matchers.shouldBe
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

infix fun LocalDateTime.shouldBeJustBefore(date: LocalDateTime) {
    shouldBeAfter(date - Duration.ofSeconds(5))
    shouldNotBeAfter(date)
}

infix fun LocalDateTime.shouldBeJustAfter(date: LocalDateTime) {
    shouldNotBeBefore(date)
    shouldBeBefore(date + Duration.ofSeconds(5))
}

infix fun LocalDateTime.shouldBeAround(date: LocalDateTime) {
    truncatedTo(ChronoUnit.SECONDS) shouldBe date.truncatedTo(ChronoUnit.SECONDS)
}
