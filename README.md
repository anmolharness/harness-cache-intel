# Cache Intelligence Test Application

A Maven-based Java application designed to generate 30GB+ of cache for testing cache intelligence systems.

## Overview

This project contains:
- A simple "Hello World" Java application
- **Maven build that generates 30GB of cache files during compilation**

## Cache Generation Strategy

**Cache is generated during Maven build time** using `maven-antrun-plugin`:

During the `compile` phase, Maven executes `generate-cache.sh` which creates 30GB of dummy cache files:
- Script: `generate-cache.sh`
- Generates 300 files × 100MB each = 30GB
- Uses `/dev/urandom` for data generation
- Progress logged every 10 files
- Output location: `~/.m2/repository/cache-data/`
- Each file: `cache-0.bin`, `cache-1.bin`, ..., `cache-299.bin`

This approach replicates real-world scenarios where:

### What Gets Generated:
- **Location**: `~/.m2/repository/cache-data/`
- **Files**: 300 × 100MB binary files
- **Total Size**: 30GB
- **Generation Time**: 5-10 minutes (depending on disk I/O)
- **File Names**: `cache-0.bin`, `cache-1.bin`, ..., `cache-299.bin`

## Prerequisites

- **Java 17 or higher**
- **Maven 3.6+**
- **At least 35 GB of free disk space**
- **Good internet connection** (will download 30GB+)

## Build

Generate 30GB cache (this will take 5-10 minutes depending on disk I/O):

```bash
mvn clean install
```

This single command will:
1. Compile the Java application
2. Execute maven-antrun-plugin during compile phase
3. Generate 30GB of cache files in `~/.m2/repository/cache-data/`
4. Package application as `target/cache-intel-1.0.0.jar`

**Build Progress:**
```
[INFO] --- maven-antrun-plugin:3.1.0:run (generate-30gb-cache) @ cache-intel ---
Starting 30GB cache generation...
Generated 10 files, total: 0GB
Generated 20 files, total: 1GB
Generated 30 files, total: 2GB
...
Generated 300 files, total: 29GB
30GB cache generation complete!
30G     /root/.m2/repository/cache-data
```

## Run

After building, you can run the simple Hello World application:

```bash
java -jar target/cache-intel-1.0.0.jar
```

Or:

```bash
mvn exec:java -Dexec.mainClass="com.harness.cachetest.CacheTestApp"
```

## Cache Location

Generated cache: `~/.m2/repository/cache-data/`

Structure:
```
~/.m2/repository/cache-data/
├── cache-0.bin     (100MB)
├── cache-1.bin     (100MB)
├── cache-2.bin     (100MB)
├── ...
└── cache-299.bin   (100MB)

Total: 30GB
```

## Verification

Check the cache size:

```bash
du -sh ~/.m2/repository/cache-data/
# Expected: 30G

# Count files
ls -1 ~/.m2/repository/cache-data/ | wc -l
# Expected: 300

# Check individual file size
ls -lh ~/.m2/repository/cache-data/ | head -5
# Each file: 100M
```

## Resource Requirements

### Build Phase (Cache Generation)
- **Memory**: 2GB RAM (minimal dependencies)
- **CPU**: 2 cores
- **Disk I/O**: Important (generating 30GB)
- **Disk Space**: 35GB free minimum
- **Time**: 5-10 minutes (disk I/O bound)

### Runtime Phase
- **Memory**: 1GB RAM
- **CPU**: 1 core
- **Time**: < 1 second

## Purpose

This application is designed for testing:
- **Harness Build Cache Intelligence** - Test cache save/restore with 30GB
- **Cache performance** - Measure 30GB cache restore time (~10 minutes)
- **GCS/S3 performance** - Test large blob upload/download
- **Container resources** - Validate disk space and I/O requirements

## Use Case

Replicates customer scenario where:
- Maven cache is 30GB
- Cache restore takes ~10 minutes
- Need to test cache intelligence optimization strategies

## Harness Pipeline Configuration

```yaml
resources:
  limits:
    memory: 4Gi
    cpu: "2"
timeout: 30m  # Build + 30GB generation takes 5-10 min
```

If using manual cache steps:

```yaml
- step:
    type: SaveCacheGCS
    spec:
      sourcePaths:
        - /root/.m2/repository/cache-data
      key: cache-30gb-{{ checksum "pom.xml" }}
      
- step:
    type: RestoreCacheGCS
    spec:
      key: cache-30gb-{{ checksum "pom.xml" }}
      # This will take ~10 minutes for 30GB
```
