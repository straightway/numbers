# numbers

This kotlin library provides extension methods
and operators for calculation with mixed number
types without the necessity to cast. As result
type, always the 'larger' type is used, e.g.
multiplying an Int with a Float results in a Float.

## Status

This software is in pre-release state. Every aspect of it may change without announcement or notification or downward
compatibility. As soon as version 1.0 is reached, all subsequent changes for sub versions will be downward compatible. Breaking changes will then only occur with a new major version with according deprecation marking.

## Include in gradle builds

To include this library in a gradle build, add

    repositories {
        ...
        maven { url "https://straightway.github.io/repo" }
    }

Then you can simply configure in your dependencies:

    dependencies {
        compile "straightway:numbers:0.1+"
    }
