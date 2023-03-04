# Test

provides enhancements to [Kotest](https://kotest.io) and [Testcontainers](https://www.testcontainers.org).

## Kotest VS Junit

[Kotest](https://kotest.io) enables us to write concise and beautiful tests in Kotlin with all kind of styles.
It should be our first choice when writing tests.

Most JUnit test cases can be migrated to Kotest seamlessly.
However, some integration tests with Spring may be hard to rewrite with Kotest,
especially when Spring annotations are doing magic behind the scenes.
In these cases, you may have to add some boilerplate code as a workaround.
So it is pragmatic to keep these tests in JUnit and refactor them later when better solutions are available.

In a nutshell, write tests with Kotest as long as possible and treat JUnit as the last resort.

## Data-Driven Testing

Kotest provides first-class support
for [data driven testing](https://kotest.io/docs/framework/datatesting/data-driven-testing.html),
which can help us avoid tedious boilerplate code.

See [here](../util/src/test/kotlin/com/example/util/JsonTest.kt) for some examples about data-driven testing.

## Property Testing

Kotest has support for [property-based testing](https://kotest.io/docs/proptest/property-based-testing.html),
which can improve the quality of our tests and cover more edge cases.

See [here](../util/src/test/kotlin/com/example/util/JsonTest.kt) for some examples about property testing.

## Integration Testing

Instead of managing a test environment manually,
we use [Testcontainers](https://www.testcontainers.org) to do integration tests.

Testcontainers provide lightweight, throwaway instances of docker containers
and help us take care of stopping containers after the testing.

Singleton containers of common databases and other external resources are provided in this library.
Check the code about how to use them.

## Mocking

Generally, mocking is not recommended because it can lead to poor code design and over-specification of tests.
It may also make tests more complex and hide real failures, providing false confidence.

Since Testcontainers make the writing of integration tests a breeze, mocking should be avoided as long as possible.

In some rare cases that you have no other choice but to mock, [MockK](https://mockk.io) may be helpful.
