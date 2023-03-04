# Util

Provides useful utility classes & functions and imports some common external libraries.

## Json

### Global `JsonMapper`

A global `JsonMapper` with some default configurations is provided.

Remember that this global mapper should never be changed.
If the default configurations don't meet your need, you can rebuild a new mapper:

```kotlin
val newJsonMapper = JSON.rebuild()
    .configure(FAIL_ON_UNKNOWN_PROPERTIES, true)
    .build()
```

### Extension functions

Some convenient extension functions about Json serialization and deserialization are also provided.

To convert any object to a Json string:

```kotlin
val obj = mapOf("foo" to "bar")
val jsonString = obj.toJson()
println(jsonString) // {"foo":"bar"}
```

To parse a Json string to the target type:

```kotlin
val jsonString = "{\"foo\":\"bar\"}"
val obj = jsonString.toJsonObject<Map<String, String>>()
println(obj) // {foo=bar}
```

Note that the caller is responsible for handling any exception during Json processing.

## Log

The function `logger()` can be used to get a logger named corresponding to the caller class.

You can set the logger as a private property:

```kotlin
class SomeClass {
    private val log = logger()
}
```

Or put it in the companion object to gain better performance:

```kotlin
class SomeClass {
    companion object {
        private val log = logger()
    }
}
```
