# Web

Autoconfigures common filters for REST API and provide extension functions for request handling to reduce boilerplate.

## CORS

A default CORS configuration has been set up to permit all cross-origin requests.

You can customize the rules by overriding the following application properties:

```yaml
web:
  cors:
    allowed-origin-patterns:
      - "https://*.domain1.com"
      - "https://*.domain2.com"
    allowed-methods: GET, POST
    allowed-headers: Token
    allow-credentials: false
```

## Error Code

You can throw `HttpException` to return errors to clients.

To define your own error code, it is recommended to implement `ErrorCode`.
Below is an example of creating an enum class of error codes:

```kotlin
enum class ExampleErrorCode(override val code: Int, override val message: String) : ErrorCode {
    ERROR_FOO(99001, "Bad things happened"),
    ERROR_BAR(99002, "Really bad things happened"),
    ;

    override val status: HttpStatus = HttpStatus.BAD_REQUEST
}
```

## Response Format

SUCCESS (HTTP STATUS 200):

```json
{
  "data": { "foo": "bar" }
}
```

ERROR (HTTP STATUS 4xx):

```json
{
  "code": 10001,
  "message": "Invalid Params"
}
```
