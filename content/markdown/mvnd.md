# Maven Daemon

The Maven Daemon (mvnd) is a daemon infrastructure for Maven that helps to reduce the build time by:
- Keeping the JVM running between builds
- Managing a pool of Maven processes
- Reusing the JVM and Maven processes across builds

## Features

- Significantly faster builds compared to regular Maven
- Compatible with existing Maven plugins and extensions
- Daemon process management
- Intelligent memory management
- Native executable available

## Installation

You can download Maven Daemon from our [download page](/download.html#Maven_Daemon).

For more detailed information and documentation, visit the [Maven Daemon GitHub repository](https://github.com/apache/maven-mvnd).

## Usage

Instead of using the `mvn` command, use `mvnd`:

```bash
mvnd clean install
```

The daemon will stay alive in the background, ready to process subsequent builds much faster.