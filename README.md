# Cache Intelligence Test Application

A Maven-based Java application designed to generate 30GB+ of Maven build cache for testing cache intelligence systems.

## Overview

This project contains:
- A simple "Hello World" Java application
- **200+ Maven dependencies totaling 30GB+** downloaded during build

## Cache Generation Strategy

**All cache is generated during Maven build time** (`mvn clean install`)

Maven will download 30GB+ of actual JAR dependencies to `~/.m2/repository/` including:

### Major Dependencies (~30GB Total):
- **Apache Spark** - Multiple versions (2.12, 2.13) with all modules (~3GB)
- **Apache Hadoop** - Multiple versions with HDFS, YARN, MapReduce (~4GB)
- **Apache Flink** - Multiple versions with connectors (~3GB)
- **AWS SDK v1 & v2** - S3, EC2, DynamoDB, Lambda, SQS, SNS, Kinesis, etc. (~6GB)
- **Google Cloud SDK** - Storage, BigQuery, Pub/Sub, Firestore, Compute (~2GB)
- **Azure SDK** - Blob Storage, Cosmos DB, Event Hubs (~1.5GB)
- **TensorFlow** - Multiple versions (~2.5GB)
- **Apache Beam** - Multiple runners (Direct, Dataflow, Spark, Flink) (~2GB)
- **Spring Boot & Cloud** - Multiple versions with all starters (~2GB)
- **Apache Camel** - Multiple components (~1.5GB)
- **Apache Hive** - Exec and Metastore (~500MB)
- **Apache HBase** - Client and Server (~400MB)
- **Elasticsearch** - Full suite (~600MB)
- **Apache Kafka** - Multiple versions (~500MB)
- **Apache Cassandra** - Driver with query builder (~300MB)
- **MongoDB** - Sync and Reactive drivers (~200MB)
- **Apache Solr** - Client and Core (~300MB)
- **Neo4j** - Java driver (~200MB)
- Plus: Netty, Jackson, Guava, Commons libraries, and more

## Prerequisites

- **Java 17 or higher**
- **Maven 3.6+**
- **At least 35 GB of free disk space**
- **Good internet connection** (will download 30GB+)

## Build

Download all dependencies (this will take 15-45 minutes depending on internet speed):

```bash
mvn clean install
```

This single command will:
1. Download 30GB+ of JAR files to `~/.m2/repository/`
2. Compile the Java application
3. Package it as `target/cache-intel-1.0.0.jar`

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

All Maven dependencies: `~/.m2/repository/`

Example structure:
```
~/.m2/repository/
├── org/apache/spark/
├── org/apache/hadoop/
├── org/apache/flink/
├── com/amazonaws/
├── com/google/cloud/
├── com/azure/
├── org/tensorflow/
└── ... (30GB+ of JARs)
```

## Verification

Check the total cache size:

```bash
du -sh ~/.m2/repository/
```

Expected output: **~30GB or more**

To see the largest dependencies:
```bash
du -sh ~/.m2/repository/* | sort -hr | head -20
```

## Resource Requirements

### Build Phase
- **Memory**: 8GB RAM recommended (minimum 4GB)
- **CPU**: 4 cores recommended (minimum 2)
- **Network**: Good bandwidth (downloading 30GB)
- **Time**: 15-45 minutes depending on internet speed

### Runtime Phase
- **Memory**: 2GB RAM
- **CPU**: 1 core
- **Time**: < 1 second

## Purpose

This application is designed for testing:
- **Cache intelligence systems** - Harness CI/CD cache optimization
- **Dependency management** - Maven caching strategies
- **Build performance** - Impact of large dependency trees
- **Storage and retrieval** - Cache size impact on build times
