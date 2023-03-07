# Demo

A demo about how to write a Spring Boot Webflux application with Kotlin.

## Overview

The demo provides simple CRUD REST APIs to manage books stored in PostgreSQL.

TODO: Add another resource stored in MongoDB.

## Running The App

1. Make sure you are in the `demo` directory.

    ```bash
    $ cd app/demo
    ```

2. Start up docker containers:

    ```bash
    $ docker compose up -d
    ```

3. Copy `application-dev.yml` to `application-local.yml` in the `resources` directory.

    ```bash
    $ cp src/main/resources/application-dev.yml src/main/resources/application-local.yml
    ```

4. Start up the application with Gradle:

    ```bash
    $ ../../gradlew bootRun
    ```

5. Visit [Actuator API](http://localhost:8080/actuator) in your browser, and you will see the application is alive.
