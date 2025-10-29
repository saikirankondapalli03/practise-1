# Kafka Core Concepts for Interviews

## Architecture Fundamentals

### Kafka Cluster Components
```
Producer → Broker (Leader/Follower) → Consumer
           ↓
       ZooKeeper/KRaft (Metadata)
```

**Key Points:**
- **Broker**: Kafka server storing/serving data
- **Controller**: Elected broker managing partition leadership
- **ISR**: In-Sync Replicas (replicas caught up with leader)
- **HWM**: High Water Mark (last committed offset)

### Topics & Partitions
```
Topic: user-events (3 partitions)
├── P0: [msg1, msg3, msg5] → Consumer A
├── P1: [msg2, msg4, msg6] → Consumer B  
└── P2: [msg7, msg8, msg9] → Consumer C
```

**Critical Rules:**
- Ordering guaranteed **within partition only**
- Max consumers = number of partitions
- Partition key determines message placement: `hash(key) % partitions`

## Producer Essentials

### Key Configurations
```java
// Reliability (Exactly-Once)
props.put("acks", "all");
props.put("enable.idempotence", true);
props.put("retries", Integer.MAX_VALUE);

// Performance (High Throughput)
props.put("batch.size", 65536);
props.put("linger.ms", 20);
props.put("compression.type", "lz4");

// Performance (Low Latency)
props.put("batch.size", 1);
props.put("linger.ms", 0);
props.put("acks", "1");
```

### Acknowledgment Levels
- **acks=0**: Fire-and-forget (fastest, data loss possible)
- **acks=1**: Leader ack (balanced)
- **acks=all**: All ISR ack (slowest, most reliable)

## Consumer Essentials

### Consumer Groups & Rebalancing
```java
// Key configs
props.put("group.id", "my-group");
props.put("auto.offset.reset", "earliest");
props.put("enable.auto.commit", "false");

// Rebalance optimization
props.put("partition.assignment.strategy", "CooperativeSticky");
props.put("session.timeout.ms", 30000);
props.put("heartbeat.interval.ms", 3000);
```

**Rebalance Triggers:**
- Consumer joins/leaves group
- Consumer heartbeat timeout
- Partition count changes

### Offset Management
```java
// Manual commit patterns
consumer.commitSync();                    // Blocking
consumer.commitAsync();                   // Non-blocking
consumer.commitSync(Collections.singletonMap(partition, offset)); // Specific offset
```

## Delivery Semantics

### At-Most-Once (Fast, May Lose)
```java
// Producer: No retries
props.put("acks", "0");
props.put("retries", 0);

// Consumer: Commit before processing
consumer.commitSync();
processMessage(record);
```

### At-Least-Once (Balanced, May Duplicate)
```java
// Producer: Basic reliability
props.put("acks", "1");
props.put("retries", 3);

// Consumer: Commit after processing
processMessage(record);
consumer.commitSync();
```

### Exactly-Once (Slow, No Duplicates)
```java
// Producer: Idempotent + Transactions
props.put("enable.idempotence", true);
props.put("transactional.id", "my-tx-id");

// Streams: EOS processing
props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, 
          StreamsConfig.EXACTLY_ONCE_V2);
```

## Kafka Streams Core

### Stream vs Table
```java
// KStream: Event stream (immutable facts)
KStream<String, String> events = builder.stream("user-events");

// KTable: Changelog stream (current state)
KTable<String, String> users = builder.table("users");
```

### Essential Operations
```java
// Stateless transformations
stream.filter((k, v) -> v.contains("error"))
      .mapValues(String::toUpperCase)
      .selectKey((k, v) -> extractUserId(v));

// Stateful aggregation
stream.groupByKey()
      .aggregate(
          () -> 0L,
          (k, v, agg) -> agg + 1L,
          Materialized.as("counts-store")
      );
```

### Windowing Types
```java
// Tumbling (non-overlapping)
TimeWindows.of(Duration.ofMinutes(5))
// [00:00-00:05), [00:05-00:10)

// Hopping (overlapping)  
TimeWindows.of(Duration.ofMinutes(5)).advanceBy(Duration.ofMinutes(1))
// [00:00-00:05), [00:01-00:06)

// Session (activity-based)
SessionWindows.with(Duration.ofMinutes(30))
// Closes after 30min inactivity
```

### Join Types
```java
// Stream-Stream (temporal)
leftStream.join(rightStream, joiner, 
    JoinWindows.of(Duration.ofMinutes(5)));

// Stream-Table (enrichment)
stream.join(table, (streamVal, tableVal) -> 
    streamVal + "-" + tableVal);

// Table-Table (current state)
leftTable.join(rightTable, joiner);
```

## Time Concepts

### Time Types
- **Event Time**: When event occurred (business time)
- **Processing Time**: When Kafka processes message
- **Stream Time**: Max timestamp seen in stream

```java
// Custom timestamp extractor
public class EventTimeExtractor implements TimestampExtractor {
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        return extractTimestampFromPayload(record.value());
    }
}
```

### Grace Periods
```java
// Handle late records
TimeWindows.of(Duration.ofMinutes(5))
          .grace(Duration.ofMinutes(1));
```

## Avro Serialization

### Why Avro?
- **Schema Evolution**: Backward/forward compatibility
- **Compact**: Binary format, smaller than JSON
- **Fast**: No parsing overhead
- **Type Safety**: Compile-time schema validation

### Schema Registry Integration
```java
// Producer with Avro
props.put("key.serializer", StringSerializer.class);
props.put("value.serializer", KafkaAvroSerializer.class);
props.put("schema.registry.url", "http://localhost:8081");

// Consumer with Avro
props.put("key.deserializer", StringDeserializer.class);
props.put("value.deserializer", KafkaAvroDeserializer.class);
props.put("specific.avro.reader", "true");
```

### Schema Definition
```json
{
  "type": "record",
  "name": "User",
  "fields": [
    {"name": "id", "type": "string"},
    {"name": "email", "type": "string"},
    {"name": "age", "type": ["null", "int"], "default": null}
  ]
}
```

### Schema Evolution Rules
```java
// BACKWARD: New schema can read old data
// - Remove fields (with defaults)
// - Add optional fields

// FORWARD: Old schema can read new data  
// - Add fields
// - Remove optional fields

// FULL: Both backward and forward compatible
// - Add/remove optional fields only
```

### Usage Example
```java
// Generate classes from schema
User user = User.newBuilder()
    .setId("123")
    .setEmail("user@example.com")
    .setAge(25)
    .build();

// Send Avro object
producer.send(new ProducerRecord<>("users", user.getId(), user));

// Consume Avro object
User receivedUser = (User) record.value();
System.out.println(receivedUser.getEmail());
```

## Performance Tuning

### Producer Optimization
```java
// Throughput focus
props.put("batch.size", 65536);
props.put("linger.ms", 20);
props.put("compression.type", "lz4");
props.put("buffer.memory", 67108864);

// Latency focus  
props.put("batch.size", 1);
props.put("linger.ms", 0);
props.put("compression.type", "none");
```

### Consumer Optimization
```java
// Throughput focus
props.put("fetch.min.bytes", 50000);
props.put("max.poll.records", 1000);
props.put("fetch.max.wait.ms", 500);

// Processing optimization
props.put("session.timeout.ms", 30000);
props.put("max.poll.interval.ms", 300000);
```

### Streams Optimization
```java
// Performance configs
props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 4);
props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 10 * 1024 * 1024);
props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);
```

## Error Handling Patterns

### Dead Letter Queue
```java
stream.mapValues(value -> {
    try {
        return processValue(value);
    } catch (Exception e) {
        dlqProducer.send(new ProducerRecord<>("dlq-topic", key, value));
        return null;
    }
}).filter((k, v) -> v != null);
```

### Retry with Backoff
```java
RetryTemplate.builder()
    .maxAttempts(3)
    .exponentialBackoff(1000, 2, 10000)
    .build()
    .execute(context -> producer.send(record).get());
```

## Key Metrics to Monitor

### Producer Metrics
- `record-send-rate`: Messages/sec sent
- `record-error-rate`: Failed sends/sec
- `request-latency-avg`: Average request latency
- `batch-size-avg`: Average batch size

### Consumer Metrics  
- `records-consumed-rate`: Records/sec consumed
- `records-lag-max`: Max lag across partitions
- `fetch-latency-avg`: Average fetch latency
- `commit-latency-avg`: Average commit latency

### Streams Metrics
- `process-rate`: Records processed/sec
- `process-latency-avg`: Processing latency
- `skipped-records-rate`: Skipped records/sec
- `commit-latency-avg`: State store commit latency

## Testing Strategies

### Unit Testing Streams
```java
TopologyTestDriver testDriver = new TopologyTestDriver(topology, props);
TestInputTopic<String, String> input = testDriver.createInputTopic("input", 
    Serdes.String().serializer(), Serdes.String().serializer());
TestOutputTopic<String, String> output = testDriver.createOutputTopic("output",
    Serdes.String().deserializer(), Serdes.String().deserializer());

input.pipeInput("key", "value");
assertEquals("expected", output.readValue());
```

### Integration Testing
```java
@Testcontainers
class KafkaIntegrationTest {
    @Container
    static KafkaContainer kafka = new KafkaContainer(
        DockerImageName.parse("confluentinc/cp-kafka:latest"));
    
    @Test
    void testProducerConsumer() {
        String servers = kafka.getBootstrapServers();
        // Test with real Kafka
    }
}
```

## Common Pitfalls & Solutions

### Hot Partitions
**Problem**: Uneven load distribution
**Solutions**: 
- Better partition key design
- Add randomness: `key + "-" + (timestamp % 10)`
- Increase partitions
- Custom partitioner for known hot keys

### Consumer Lag
**Problem**: Consumers falling behind
**Solutions**: 
- Scale consumer instances (up to partition count)
- Optimize processing logic
- Tune `max.poll.records` and `fetch.min.bytes`
- Parallel processing within consumer

### Rebalancing Issues
**Problem**: Frequent rebalances causing downtime
**Solutions**:
- Use `CooperativeSticky` assignment
- Increase `session.timeout.ms` and `max.poll.interval.ms`
- Optimize consumer processing time
- Avoid blocking operations in consumer loop

### Memory Issues in Streams
**Problem**: OutOfMemoryError in Streams apps
**Solutions**:
- Reduce `cache.max.bytes.buffering`
- Increase `commit.interval.ms`
- Use RocksDB for large state stores
- Tune JVM heap size

### Duplicate Messages
**Problem**: Receiving same message multiple times
**Causes**: Producer retries, consumer rebalancing, offset commit failures
**Solutions**:
```java
// Idempotent processing
Set<String> processedIds = new HashSet<>();
if (!processedIds.contains(messageId)) {
    processMessage(record);
    processedIds.add(messageId);
}
```

### Message Loss
**Problem**: Messages disappearing
**Causes**: `acks=0`, insufficient replicas, retention policy
**Solutions**:
```java
props.put("acks", "all");
props.put("min.insync.replicas", "2");
props.put("retries", Integer.MAX_VALUE);
```

### Slow Consumer Processing
**Problem**: Consumer can't keep up with producer rate
**Solutions**:
```java
// Async processing
ExecutorService executor = Executors.newFixedThreadPool(10);
records.forEach(record -> 
    executor.submit(() -> processRecord(record)));

// Batch processing
List<ConsumerRecord> batch = new ArrayList<>();
for (ConsumerRecord record : records) {
    batch.add(record);
    if (batch.size() >= 100) {
        processBatch(batch);
        batch.clear();
    }
}
```

### Wrong Partition Assignment
**Problem**: Related messages on different partitions
**Solution**: Ensure same key for related messages
```java
// Use consistent key
String partitionKey = extractCustomerId(message);
producer.send(new ProducerRecord<>(topic, partitionKey, message));
```

### Offset Commit Failures
**Problem**: `CommitFailedException` during rebalancing
**Solutions**:
```java
// Reduce processing time
props.put("max.poll.records", 100);

// Handle commit failures gracefully
try {
    consumer.commitSync();
} catch (CommitFailedException e) {
    // Rebalance happened, continue processing
}
```

### Schema Evolution Issues
**Problem**: Incompatible schema changes breaking consumers
**Solutions**:
- Use Schema Registry with compatibility checks
- Only make backward-compatible changes
- Version your schemas properly
```java
// Backward compatible: add optional fields only
{
  "name": "User",
  "fields": [
    {"name": "id", "type": "string"},
    {"name": "email", "type": "string"},
    {"name": "phone", "type": ["null", "string"], "default": null}
  ]
}
```

### Broker Disk Space Issues
**Problem**: Brokers running out of disk space
**Solutions**:
- Set appropriate retention policies
- Monitor disk usage
- Use log compaction for reference data
```properties
log.retention.hours=168
log.segment.bytes=1073741824
log.cleanup.policy=delete
```

### Network Timeouts
**Problem**: Frequent timeout exceptions
**Solutions**:
```java
// Increase timeouts
props.put("request.timeout.ms", 60000);
props.put("delivery.timeout.ms", 120000);
props.put("session.timeout.ms", 30000);

// Retry configuration
props.put("retries", Integer.MAX_VALUE);
props.put("retry.backoff.ms", 1000);
```

### Streams State Store Corruption
**Problem**: State store becomes corrupted
**Solutions**:
- Enable changelog topics for backup
- Set appropriate cleanup policies
- Handle `InvalidStateStoreException`
```java
props.put(StreamsConfig.STATE_DIR_CONFIG, "/tmp/kafka-streams");
props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 3);

// Graceful handling
try {
    store.get(key);
} catch (InvalidStateStoreException e) {
    // State store not ready, retry later
}
```

### Join Window Misses
**Problem**: Stream-stream joins missing expected matches
**Causes**: Clock skew, wrong window size, late data
**Solutions**:
```java
// Larger join window
JoinWindows.of(Duration.ofMinutes(10))
          .grace(Duration.ofMinutes(2));

// Use event time, not processing time
props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG,
          WallclockTimestampExtractor.class);
```

### Producer Buffer Overflow
**Problem**: `BufferExhaustedException` when sending
**Solutions**:
```java
// Increase buffer size
props.put("buffer.memory", 134217728); // 128MB

// Block when buffer full
props.put("max.block.ms", 60000);

// Handle overflow gracefully
try {
    producer.send(record);
} catch (BufferExhaustedException e) {
    // Implement backpressure
    Thread.sleep(100);
}
```

### Avro Schema Compatibility Issues
**Problem**: Schema evolution breaking consumers
**Solutions**:
```java
// Check compatibility before deployment
SchemaRegistryClient client = new CachedSchemaRegistryClient("http://localhost:8081", 100);
boolean compatible = client.testCompatibility("user-value", newSchema);

// Use proper compatibility mode
// BACKWARD: New schema reads old data
// FORWARD: Old schema reads new data
// FULL: Both directions work
```

### Schema Registry Unavailable
**Problem**: Cannot serialize/deserialize when Schema Registry is down
**Solutions**:
```java
// Cache schemas locally
props.put("schema.registry.cache.capacity", 1000);

// Fallback serialization
public class FallbackAvroSerializer extends KafkaAvroSerializer {
    @Override
    public byte[] serialize(String topic, Object data) {
        try {
            return super.serialize(topic, data);
        } catch (Exception e) {
            // Fallback to JSON or cached schema
            return fallbackSerialize(data);
        }
    }
}
```