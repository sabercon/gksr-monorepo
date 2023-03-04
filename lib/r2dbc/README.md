# R2DBC

Autoconfigures R2DBC framework with PostgresSQL and Flyway.

## Flyway

[Flyway](https://flywaydb.org/documentation) are used to make SQL migrations easy.

### Configuration

Flyway is enabled automatically, add the below properties if you want to disable it:

```yaml
spring.flyway.enabled: false
```

Flyway can only use JDBC DataSource,
but an `ApplicationListener` has been autoconfigured to add JDBC properties by parsing `spring.r2dbc.url`.
So no extra database configuration is required.

In case you want to use different data sources, override the below application properties:

```yaml
spring:
  flyway:
    url: jdbc:postgresql://localhost:5432/postgres
    user: postgres
    password: postgres
```

In a project where multiple people frequently add migration scripts simultaneously,
activating the `outOfOrder` mode can reduce conflicts.

```yaml
spring.flyway.out-of-order: true
```

But be careful, migration may not be reproducible under this mode.
Unexpected errors may occur when multiple scripts modify the same tables in different orders.

See [here](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization.migration-tool.flyway)
for more settings provided by SpringBoot.

### Writing Migrations Scripts

Migrations are typically scripts in the format `V<VERSION>__<NAME>.sql`,
with `<VERSION>` being an underscore-separated version (e.g. `2_1`).

By default, migrations are stored in a directory called `classpath:db/migration`.
However, you can customize this location by setting the `spring.flyway.locations` property.

You can also use timestamps as versions to avoid version conflicts. For example, `V202303031200__Init.sql`.

### Pitfalls

Some scripts may take a long time to process and cause a timeout during Flyway migration,
such as when creating an index on a very large table.

To prevent such issues, it is recommended that you ask a DBA to review
and directly execute time-consuming scripts in the database instead.
However, for consistency across all environments,
you should still add a migration script in Flyway and ensure that it is idempotent.

For example, if you need to create a unique index,
you can submit a request to create it in the database beforehand and then add the following script to Flyway migrations:

```sql
CREATE UNIQUE INDEX IF NOT EXISTS "some_uindex" on "table_with_one_billion_record" ("some_column");
```
