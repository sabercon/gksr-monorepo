# Mongo

Autoconfigures Spring Data MongoDB.

## Configuration

Add the below properties:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017
      database: local
```

Auto index creation has been enabled.

`BigDecimal` will be converted to `Decimal128` by default.

## Usage

See [here](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/) for how to use Spring Data MongoDB.
