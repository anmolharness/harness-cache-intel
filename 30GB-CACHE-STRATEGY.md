# 30GB Maven Cache Strategy

## Problem
The original pom.xml had 200+ dependencies but only resulted in **1.3GB** in `.m2/repository` due to Maven's dependency deduplication.

## Root Cause
When you add multiple frameworks (Spark, Hadoop, Flink, etc.) with the SAME major version:
- They share many common dependencies (Netty, Jackson, Guava, etc.)
- Maven downloads each shared dependency **only once**
- Result: Much less than expected total size

## Solution
Add **DIFFERENT MAJOR VERSIONS** of large frameworks. Different major versions have incompatible dependency trees, so Maven **cannot deduplicate** them.

## New Strategy

### Spark: 2.x vs 3.x (~8GB)
```
spark 2.4.8 (Scala 2.11) → Uses old Netty 4.0, old Jackson 2.6
spark 3.3.4 (Scala 2.12) → Uses Netty 4.1, Jackson 2.13
spark 3.5.0 (Scala 2.13) → Uses different Scala stdlib

Result: 3 completely separate dependency trees = ~8GB
```

### Hadoop: 2.x vs 3.x (~6GB)
```
hadoop 2.10.2 → Old Guava 11, old Commons
hadoop 3.3.6  → New Guava 32, new Commons

Result: 2 separate trees = ~6GB
```

### Flink: 1.14 vs 1.17 (~4GB)
```
flink 1.14 → Scala 2.12 only
flink 1.17 → Different runtime, updated deps

Result: 2 separate trees = ~4GB
```

### AWS SDK: v1.11 vs v1.12 (~3GB)
```
aws-java-sdk 1.11.1000 (full SDK)
aws-java-sdk 1.12.600  (full SDK)

Result: 2 complete SDK downloads = ~3GB
```

### Elasticsearch: 6.x vs 7.x vs 8.x (~2GB)
```
elasticsearch 6.8.23 → Lucene 7
elasticsearch 7.17.15 → Lucene 8
elasticsearch 8.11.0 → Lucene 9

Result: 3 separate trees = ~2GB
```

### Spring Boot: 2.x vs 3.x (~2GB)
```
spring-boot 2.7.18 → Spring 5, javax.* packages
spring-boot 3.2.0  → Spring 6, jakarta.* packages

Result: 2 separate trees = ~2GB
```

### Kafka: 2.x vs 3.x (~1.5GB)
```
kafka 2.8.2 → Scala 2.12
kafka 3.6.0 → Scala 2.13, updated deps

Result: 2 separate trees = ~1.5GB
```

### Hive + HBase (~1.8GB)
```
hive-exec, hive-metastore → Large JARs with many deps
hbase-client, hbase-server → Separate dependency tree

Result: ~1.8GB
```

### Google Cloud (~1.5GB)
```
google-cloud-storage, bigquery → Large protobuf deps

Result: ~1.5GB
```

## Expected Total: ~30GB

| Framework | Versions | Size |
|-----------|----------|------|
| Spark | 2.4.8, 3.3.4, 3.5.0 | ~8GB |
| Hadoop | 2.10.2, 3.3.6 | ~6GB |
| Flink | 1.14, 1.17 | ~4GB |
| AWS SDK | 1.11, 1.12 | ~3GB |
| Elasticsearch | 6.x, 7.x, 8.x | ~2GB |
| Spring Boot | 2.7, 3.2 | ~2GB |
| Kafka | 2.8, 3.6 | ~1.5GB |
| Hive + HBase | 3.1.3, 2.5.6 | ~1.8GB |
| Google Cloud | 2.x | ~1.5GB |
| **TOTAL** | | **~30GB** |

## Build Instructions

```bash
# This will download ~30GB to ~/.m2/repository
mvn clean install -DskipTests

# Verify cache size
du -sh ~/.m2/repository
# Expected: ~30GB
```

## Build Time Estimate
- **First build**: 30-60 minutes (downloading 30GB)
- **With Harness cache**: ~10 minutes (restoring 30GB from GCS)

## Key Insight
To replicate customer's 30GB cache:
1. Use **different major versions** of large frameworks
2. Each major version = separate dependency tree
3. Maven cannot deduplicate across major versions
4. Result: Actual 30GB in .m2/repository
