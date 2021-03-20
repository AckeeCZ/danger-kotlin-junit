[ ![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.ackeecz/danger-kotlin-junit/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.ackeecz/danger-kotlin-junit)

# danger-kotlin junit plugin

Plugin for [danger-kotlin](https://github.com/danger/kotlin) parsing and reporting JUnit results.

## Installation

Put

```kotlin
@file:DependsOn("io.github.ackeecz:danger-kotlin-junit:x.y.z")
```

to the top of your Dangerfile

## Usage

First you need to register the plugin via

```kotlin
register plugin JUnitPlugin
```

and then you can use it through its public methods

```kotlin
JUnitPlugin.parse(junitReportFile)
JUnitPlugin.report()
```

`parse` method accepts varargs of files pointing to the junit reports and parses them to internal representation.

`report` methods will process parsed results and reports them to pull request comments.

Example Dangerfile

```kotlin
@file:DependsOn("io.github.ackeecz:danger-kotlin-junit:x.y.z")

import io.github.ackeecz.danger.junit.JUnitPlugin

import systems.danger.kotlin.danger
import systems.danger.kotlin.register

import java.nio.file.Files
import java.nio.file.Paths
import java.util.function.BiPredicate
import java.util.stream.Collectors

register plugin JUnitPlugin

danger(args) {
    val junitReports = Files.find(Paths.get(""), 10, BiPredicate { path, _ ->
        val fileName = path.toFile().name
        fileName.startsWith("TEST") && fileName.endsWith("xml")
    }).map { it.toFile() }.collect(Collectors.toList())

    JUnitPlugin.parse(*junitFiles.toTypedArray())
    JUnitPlugin.report()
}
```

This will find all files in the depth of 10 relative to current directory that matches the junit report files naming,
and it will pass them to the plugin for processing.
