# GKSR Monorepo

[![Detekt](https://github.com/sabercon/gksr-monorepo/actions/workflows/detekt.yml/badge.svg)](https://github.com/sabercon/gksr-monorepo/actions/workflows/detekt.yml)
[![Test](https://github.com/sabercon/gksr-monorepo/actions/workflows/test.yml/badge.svg)](https://github.com/sabercon/gksr-monorepo/actions/workflows/test.yml)

## Overview

This project is an example of a monorepo built using [Gradle](https://gradle.org), [Kotlin](https://kotlinlang.org),
[Spring](https://spring.io), and [Reactor](https://projectreactor.io).

## Set up

### IDE

[IntelliJ IDEA](https://www.jetbrains.com/idea/download) is highly recommended
for its excellent support of Java and Kotlin.

### JDK

The projects are developed using JDK 17 and Kotlin 1.8.x.

Follow [this guide](https://www.jetbrains.com/help/idea/sdk.html) to set up SDKs in IntelliJ IDEA.

### Docker

We use [Docker](https://www.docker.com) to set up databases and other external dependencies for local development.
Besides, most integration tests require Docker to be running.

On Mac, you can install Docker Desktop using [Homebrew](https://formulae.brew.sh/cask/docker):

```bash
$ brew install --cask docker
```

## Code Quality

### Detekt

We have integrated [Detekt](https://detekt.dev/docs/intro) to analyze code smell in our projects.
You can find the configuration file [here](./config/detekt/config.yml).

To run Detekt on all projects:

```bash
$ ./gradlew detekt
```

Usually, it's recommended to follow all the rule sets provided by Detekt.
However, sometimes certain rules may be too strict to address.
In this case, you can add a [suppression](https://detekt.dev/docs/introduction/suppressing-rules)
to temporarily suppress the issues.

If a rule is suppressed too often, it's best to open a Pull Request to discuss with your teammates
about disabling it completely in the configuration file.

### Detekt IntelliJ Plugin

Detekt comes with an [IntelliJ Plugin](https://plugins.jetbrains.com/plugin/10761-detekt)
that you can install directly from the IDE.

The plugin offers warning highlight directly inside the IDE as well as support for code formatting.

See [here](https://github.com/detekt/detekt-intellij-plugin) for how to enable the plugin.

### Detekt Git Hook

If you always forget to run Detekt task before committing your code,
follow [this guide](https://detekt.dev/docs/gettingstarted/git-pre-commit-hook) to set up a `pre-commit` Git Hook.

## Test

To run tests from all projects:

```bash
$ ./gradlew test
```

### Kotest

Most tests in our projects are written with [Kotest](https://kotest.io/docs/quickstart).

Check [here](./common/test/README.md) for some practices on how to write better tests with Kotest.

### Kotest IntelliJ Plugin

Kotest offers an [IntelliJ plugin](https://plugins.jetbrains.com/plugin/14080-kotest)
that is available on the JetBrains plugin marketplace.

This plugin provides run icons for each test, a tool window for test navigation,
duplicated test highlighting, assertion intentions, and more.

See [here](https://kotest.io/docs/intellij/intellij-plugin.html) for more details.
