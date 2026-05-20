# Cache Intelligence Test Application

A Maven-based Java application designed to generate 30GB+ of cache files for testing cache intelligence systems.

## Overview

This project contains:
- A simple "Hello World" Java application
- Maven dependencies totaling 3-4 GB (Spark, Hadoop, Flink, AWS SDK, etc.)
- Runtime cache file generation creating 30+ GB of data

## Cache Generation

The application generates cache in two ways:

1. **Maven Dependencies (~3-4 GB)**: Located in `~/.m2/repository/`
   - Apache Spark (300MB)
   - Apache Hadoop (500MB)
   - Apache Flink (400MB)
   - AWS SDK (500MB)
   - TensorFlow (500MB)
   - Spring Boot (200MB)
   - And many more large dependencies

2. **Runtime Generated Files (30+ GB)**: Located in `~/.m2/repository/cache-data/`
   - Binary cache files generated when the application runs
   - Files are created in 100MB chunks until 30GB target is reached

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- At least 35 GB of free disk space

## Build and Run

### Download all dependencies (generates ~3-4 GB cache):
```bash
mvn clean install
```

### Run the application (generates additional 30 GB):
```bash
mvn exec:java -Dexec.mainClass="com.harness.cachetest.CacheTestApp"
```

Or run the compiled jar:
```bash
java -jar target/cache-intel-1.0.0.jar
```

## Cache Locations

- Maven dependencies: `~/.m2/repository/`
- Generated cache files: `~/.m2/repository/cache-data/`

## Total Cache Size

- Maven dependencies: ~3-4 GB
- Generated files: ~30 GB
- **Total: 33-34 GB**

## Verification

To check the cache size:
```bash
du -sh ~/.m2/repository/
du -sh ~/.m2/repository/cache-data/
```

## Purpose

This application is designed for testing:
- Cache intelligence systems
- Dependency management optimization
- Build caching strategies
- Storage and retrieval performance
