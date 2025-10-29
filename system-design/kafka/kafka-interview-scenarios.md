# Kafka Interview Questions & Scenarios

## Architecture & Design Questions

### Q1: Design a real-time analytics system processing 1M events/sec
**Key Points:**
- Multiple Kafka clusters for different regions
- Partitioning strategy: `hash(userId) % partitions`
- Consumer groups with auto-scaling
- Kafka Streams for real-time aggregations
- Separate topics for different event types

**Architecture:**
```
Events → Kafka (50 partitions) → Streams App (10 instances) → Results Topic → Dashboard
```

### Q2: How do you ensure exactly-once processing in a payment system?
**Answer:**
```java
// Producer: Idempotent + Transactions
props.put("enable.idempotence", true);
props.put("transactional.id", "payment-processor-" + instanceId);

// Streams: EOS guarantee
props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, EXACTLY_ONCE_V2);

// Consumer: Transactional reads
props.put("isolation.level", "read_committed");
```

### Q3: Design event sourcing with Kafka
**Components:**
- Command Topic: User actions
- Event Topic: Domain events (log compacted)
- Snapshot Topic: Periodic state snapshots
- Projection Topics: Read models

**Pattern:**
```java
// Event store
KTable<String, UserAggregate> userState = builder
    .stream("user-events")
    .groupByKey()
    .aggregate(UserAggregate::new, this::applyEvent);
```

## Performance & Scaling Questions

### Q4: Consumer lag is growing during peak hours. How to handle?
**Investigation Steps:**
1. Check consumer metrics: `records-lag-max`
2. Monitor processing time per message
3. Check partition distribution

**Solutions:**
```java
// Increase consumer instances (up to partition count)
// Optimize processing
props.put("max.poll.records", 100);  // Smaller batches
props.put("fetch.max.wait.ms", 100); // Faster polling

// Parallel processing within consumer
CompletableFuture.allOf(
    records.stream()
        .map(record -> CompletableFuture.runAsync(() -> process(record)))
        .toArray(CompletableFuture[]::new)
).join();
```

### Q5: How to handle hot partitions?
**Detection:**
- Monitor per-partition metrics
- Check key distribution

**Solutions:**
1. **Better partitioning:**
```java
// Add randomness to hot keys
String enhancedKey = originalKey + "-" + (System.currentTimeMillis() % 10);
```

2. **Increase partitions:**
```bash
kafka-topics --alter --topic my-topic --partitions 20
```

3. **Custom partitioner:**
```java
public class LoadBalancingPartitioner implements Partitioner {
    public int partition(String topic, Object key, byte[] keyBytes, 
                        Object value, byte[] valueBytes, Cluster cluster) {
        if (isHotKey(key)) {
            return distributeHotKey(key, cluster.partitionCountForTopic(topic));
        }
        return defaultPartition(key, cluster);
    }
}
```

## Troubleshooting Scenarios

### Q6: Producer experiencing high latency - debug approach
**Investigation:**
1. Check producer metrics: `request-latency-avg`, `batch-size-avg`
2. Network latency to brokers
3. Broker-side metrics: `request-queue-size`

**Common Causes & Fixes:**
```java
// Large batches causing delays
props.put("batch.size", 16384);     // Reduce batch size
props.put("linger.ms", 5);          // Reduce linger time

// Network issues
props.put("request.timeout.ms", 30000);
props.put("delivery.timeout.ms", 120000);

// Broker overload
props.put("acks", "1");             // Reduce from "all"
```

### Q7: Consumer group constantly rebalancing
**Causes:**
- Consumer processing too slow
- Network issues
- GC pauses

**Solutions:**
```java
// Increase timeouts
props.put("session.timeout.ms", 30000);
props.put("heartbeat.interval.ms", 3000);
props.put("max.poll.interval.ms", 300000);

// Use cooperative rebalancing
props.put("partition.assignment.strategy", "CooperativeSticky");

// Optimize processing
props.put("max.poll.records", 100);  // Process smaller batches
```

### Q8: Kafka Streams app falling behind
**Diagnosis:**
```java
// Check metrics
streams.metrics().entrySet().stream()
    .filter(entry -> entry.getKey().name().equals("process-latency-avg"))
    .forEach(System.out::println);
```

**Optimizations:**
```java
// Increase parallelism
props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 8);

// Tune caching
props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 50 * 1024 * 1024);

// Optimize state stores
props.put(StreamsConfig.ROCKSDB_CONFIG_SETTER_CLASS_CONFIG, 
          CustomRocksDBConfigSetter.class);
```

## Advanced Scenarios

### Q9: Implement distributed rate limiting with Kafka
**Approach:**
```java
// Rate limiter state in KTable
KTable<String, RateLimit> rateLimits = builder.table("rate-limits");

// Check requests against limits
KStream<String, Request> allowedRequests = requests
    .join(rateLimits, (request, limit) -> {
        if (limit.allowRequest(request.timestamp())) {
            return request;
        }
        return null; // Rate limited
    })
    .filter((k, v) -> v != null);
```

### Q10: Handle schema evolution in production
**Strategy:**
```java
// Schema Registry with compatibility
props.put("schema.registry.url", "http://localhost:8081");
props.put("auto.register.schemas", false);
props.put("use.latest.version", true);

// Backward compatible changes only
// - Add optional fields
// - Remove fields (with defaults)
// - Widen field types
```

### Q11: Implement exactly-once across multiple systems
**Pattern:**
```java
// Transactional outbox pattern
@Transactional
public void processPayment(Payment payment) {
    // 1. Update database
    paymentRepository.save(payment);
    
    // 2. Write to outbox table
    outboxRepository.save(new OutboxEvent("payment-processed", payment));
}

// Separate process reads outbox → Kafka
outboxEvents.forEach(event -> {
    producer.beginTransaction();
    producer.send(new ProducerRecord<>("payments", event.getPayload()));
    producer.commitTransaction();
    outboxRepository.markProcessed(event.getId());
});
```

## Code Implementation Questions

### Q12: Implement custom Kafka Streams processor
```java
public class DeduplicationProcessor implements Processor<String, String> {
    private KeyValueStore<String, String> store;
    private ProcessorContext context;
    
    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.store = (KeyValueStore<String, String>) context.getStateStore("dedup-store");
    }
    
    @Override
    public void process(String key, String value) {
        String existing = store.get(key);
        if (existing == null) {
            store.put(key, value);
            context.forward(key, value);
        }
        // Duplicate - ignore
    }
}
```

### Q13: Implement windowed aggregation with late data handling
```java
KTable<Windowed<String>, Long> windowedCounts = stream
    .groupByKey()
    .windowedBy(TimeWindows.of(Duration.ofMinutes(5))
                          .grace(Duration.ofMinutes(1)))
    .count(Materialized.as("windowed-counts"));

// Handle late data
stream.process(() -> new Processor<String, String>() {
    @Override
    public void process(String key, String value) {
        long eventTime = extractTimestamp(value);
        long currentStreamTime = context.timestamp();
        
        if (eventTime < currentStreamTime - Duration.ofMinutes(6).toMillis()) {
            // Too late - send to late data topic
            context.forward(key, value, To.child("late-data-sink"));
        } else {
            context.forward(key, value);
        }
    }
});
```

### Q14: Implement circuit breaker for Kafka producer
```java
public class CircuitBreakerProducer {
    private final KafkaProducer<String, String> producer;
    private final CircuitBreaker circuitBreaker;
    
    public CircuitBreakerProducer(KafkaProducer<String, String> producer) {
        this.producer = producer;
        this.circuitBreaker = CircuitBreaker.ofDefaults("kafka-producer");
    }
    
    public Future<RecordMetadata> send(ProducerRecord<String, String> record) {
        return circuitBreaker.executeSupplier(() -> {
            try {
                return producer.send(record).get(5, TimeUnit.SECONDS);
            } catch (Exception e) {
                throw new RuntimeException("Send failed", e);
            }
        });
    }
}
```

## System Design Deep Dives

### Q15: Design microservices communication with Kafka
**Event-Driven Architecture:**
```
Order Service → order-events → [Inventory, Payment, Shipping] Services
                            ↓
                        Saga Orchestrator
```

**Implementation:**
```java
// Order service publishes events
producer.send(new ProducerRecord<>("order-events", 
    new OrderCreated(orderId, customerId, items)));

// Saga coordinator
stream.selectKey((k, v) -> v.getSagaId())
      .groupByKey()
      .aggregate(SagaState::new, this::handleEvent)
      .toStream()
      .filter((k, v) -> v.isComplete())
      .to("saga-completed");
```

### Q16: Implement event sourcing with snapshots
```java
// Event store
KTable<String, UserAggregate> currentState = builder
    .stream("user-events")
    .groupByKey()
    .aggregate(
        UserAggregate::new,
        (key, event, aggregate) -> aggregate.apply(event),
        Materialized.as("user-state")
    );

// Periodic snapshots
currentState.toStream()
    .filter((k, v) -> v.getVersion() % 100 == 0) // Every 100 events
    .to("user-snapshots");
```

## Situational Questions

### Q17: Your Kafka cluster is down. How do you handle it?
**Immediate Actions:**
1. Check broker health and logs
2. Verify ZooKeeper/KRaft status
3. Enable circuit breakers in applications
4. Switch to backup cluster if available

**Application Resilience:**
```java
// Producer with retry and fallback
public void sendWithFallback(ProducerRecord record) {
    try {
        producer.send(record).get(5, TimeUnit.SECONDS);
    } catch (Exception e) {
        // Store in local queue for retry
        fallbackQueue.offer(record);
        scheduleRetry();
    }
}
```

### Q18: A consumer is processing the same message repeatedly
**Root Causes:**
- Processing failure without offset commit
- Infinite retry loop
- Poison message

**Solution:**
```java
int retryCount = 0;
while (retryCount < MAX_RETRIES) {
    try {
        processMessage(record);
        consumer.commitSync();
        break;
    } catch (Exception e) {
        retryCount++;
        if (retryCount >= MAX_RETRIES) {
            // Send to DLQ
            dlqProducer.send(new ProducerRecord<>("dlq-topic", record));
            consumer.commitSync(); // Commit to skip poison message
        }
    }
}
```

### Q19: You need to migrate 1TB of data from old to new Kafka cluster
**Migration Strategy:**
1. **Dual Write Phase:**
```java
// Write to both clusters
producer1.send(record); // Old cluster
producer2.send(record); // New cluster
```

2. **Consumer Migration:**
```java
// Gradually move consumers
// Start: 100% old cluster
// Middle: 50% old, 50% new
// End: 100% new cluster
```

3. **Data Backfill:**
```bash
# Use MirrorMaker 2.0
connect-mirror-maker mm2.properties
```

### Q20: Your stream processing app has a memory leak
**Investigation:**
1. Monitor JVM heap usage
2. Check state store sizes
3. Analyze GC logs
4. Profile with tools like JProfiler

**Solutions:**
```java
// Reduce cache size
props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 10 * 1024 * 1024);

// More frequent commits
props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);

// Use RocksDB for large state
props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
```

### Q21: Producer is getting "Message too large" errors
**Causes:**
- Message exceeds `max.message.bytes`
- Batch exceeds `message.max.bytes`

**Solutions:**
```java
// Increase broker limits
max.message.bytes=10485760  // 10MB
replica.fetch.max.bytes=10485760

// Producer config
props.put("max.request.size", 10485760);

// Split large messages
public void sendLargeMessage(String key, byte[] data) {
    int chunkSize = 1024 * 1024; // 1MB chunks
    for (int i = 0; i < data.length; i += chunkSize) {
        byte[] chunk = Arrays.copyOfRange(data, i, Math.min(i + chunkSize, data.length));
        producer.send(new ProducerRecord<>(topic, key + "-" + i, chunk));
    }
}
```

### Q22: How do you handle time zone issues in global deployment?
**Strategy:**
```java
// Always use UTC for event timestamps
public class UTCTimestampExtractor implements TimestampExtractor {
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        // Extract UTC timestamp from message
        return Instant.parse(getTimestampField(record.value())).toEpochMilli();
    }
}

// Configure streams with UTC
props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, 
          UTCTimestampExtractor.class);
```

### Q23: Your topic has millions of small messages. How to optimize?
**Problems:**
- High overhead per message
- Network inefficiency
- Storage waste

**Solutions:**
```java
// Batch messages at application level
List<Event> batch = new ArrayList<>();
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

scheduler.scheduleAtFixedRate(() -> {
    if (!batch.isEmpty()) {
        EventBatch batchMessage = new EventBatch(batch);
        producer.send(new ProducerRecord<>(topic, batchMessage));
        batch.clear();
    }
}, 0, 100, TimeUnit.MILLISECONDS);

// Increase producer batching
props.put("batch.size", 65536);
props.put("linger.ms", 50);
props.put("compression.type", "lz4");
```

### Q24: Consumer group is stuck on one partition
**Diagnosis:**
```java
// Check partition assignment
consumer.assignment().forEach(partition -> 
    System.out.println("Partition: " + partition + 
                      ", Lag: " + getLag(partition)));
```

**Causes & Solutions:**
- **Slow consumer:** Scale horizontally or optimize processing
- **Hot partition:** Redistribute keys or increase partitions
- **Sticky assignment:** Use RoundRobin or Range strategy

### Q25: How do you implement blue-green deployment with Kafka?
**Strategy:**
```java
// Use consumer group names for deployment
String consumerGroup = "my-service-" + deploymentColor; // blue/green
props.put("group.id", consumerGroup);

// Gradual traffic shift
// 1. Deploy green version with new consumer group
// 2. Both blue and green consume (duplicate processing)
// 3. Verify green health
// 4. Stop blue consumers
// 5. Clean up blue consumer group offsets
```

### Q26: Schema Registry is returning 409 conflicts
**Cause:** Incompatible schema changes

**Resolution:**
```java
// Check compatibility before registering
SchemaRegistryClient client = new CachedSchemaRegistryClient(url, 100);
try {
    boolean compatible = client.testCompatibility(subject, newSchema);
    if (compatible) {
        client.register(subject, newSchema);
    } else {
        // Make schema backward compatible
        Schema fixedSchema = makeBackwardCompatible(newSchema);
        client.register(subject, fixedSchema);
    }
} catch (RestClientException e) {
    // Handle registration failure
}
```

### Q27: Your Kafka Streams app needs to join data from 5 different topics
**Challenge:** Complex join topology

**Solution:**
```java
// Star join pattern - one main stream with multiple tables
KStream<String, Order> orders = builder.stream("orders");
KTable<String, Customer> customers = builder.table("customers");
KTable<String, Product> products = builder.table("products");
KTable<String, Inventory> inventory = builder.table("inventory");
KTable<String, Pricing> pricing = builder.table("pricing");

// Sequential joins
KStream<String, EnrichedOrder> enriched = orders
    .join(customers, (order, customer) -> order.withCustomer(customer))
    .join(products, (order, product) -> order.withProduct(product))
    .join(inventory, (order, inv) -> order.withInventory(inv))
    .join(pricing, (order, price) -> order.withPricing(price));
```

### Q28: How do you handle Kafka during Black Friday traffic spike?
**Preparation:**
```java
// Pre-scale infrastructure
// - Increase partition count
// - Add broker nodes
// - Scale consumer groups

// Circuit breaker for overload protection
public class AdaptiveProducer {
    private final RateLimiter rateLimiter;
    
    public void send(ProducerRecord record) {
        if (rateLimiter.tryAcquire()) {
            producer.send(record);
        } else {
            // Drop or queue for later
            handleOverload(record);
        }
    }
}

// Auto-scaling consumer groups
if (consumerLag > threshold) {
    scaleUpConsumers();
}
```

### Q29: Implement audit logging for all Kafka operations
**Solution:**
```java
// Interceptor for all producer operations
public class AuditProducerInterceptor implements ProducerInterceptor<String, String> {
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        auditLogger.info("Sending message to topic: {} with key: {}", 
                        record.topic(), record.key());
        return record;
    }
    
    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (exception == null) {
            auditLogger.info("Message sent successfully to partition: {}", 
                           metadata.partition());
        } else {
            auditLogger.error("Failed to send message", exception);
        }
    }
}

// Configure interceptor
props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, 
          AuditProducerInterceptor.class.getName());
```

### Q30: Your team wants to implement CDC (Change Data Capture) with Kafka
**Architecture:**
```java
// Database → Debezium → Kafka → Stream Processing → Target Systems

// Debezium connector config
{
  "name": "mysql-connector",
  "config": {
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "database.hostname": "mysql",
    "database.port": "3306",
    "database.user": "debezium",
    "database.password": "dbz",
    "database.server.id": "184054",
    "database.server.name": "dbserver1",
    "database.include.list": "inventory",
    "database.history.kafka.bootstrap.servers": "kafka:9092",
    "database.history.kafka.topic": "dbhistory.inventory"
  }
}

// Process CDC events
KStream<String, ChangeEvent> changes = builder.stream("dbserver1.inventory.products");
changes.filter((k, v) -> v.getOperation().equals("UPDATE"))
       .mapValues(this::transformToBusinessEvent)
       .to("product-updates");
```
// Event store
KTable<String, UserAggregate> currentState = builder
    .stream("user-events")
    .groupByKey()
    .aggregate(
        UserAggregate::new,
        (key, event, aggregate) -> aggregate.apply(event),
        Materialized.<String, UserAggregate, KeyValueStore<Bytes, byte[]>>as("user-state")
            .withKeySerde(Serdes.String())
            .withValueSerde(userAggregateSerde)
    );

// Periodic snapshots
currentState.toStream()
    .filter((k, v) -> v.getVersion() % 100 == 0) // Every 100 events
    .to("user-snapshots");
```

## Performance Optimization Questions

### Q17: Optimize for 10x traffic increase
**Scaling Strategy:**
1. **Horizontal scaling:**
   - Increase partitions: 10 → 100
   - Scale consumer groups: 10 → 100 instances
   - Add broker nodes: 3 → 10

2. **Configuration tuning:**
```java
// Producer optimizations
props.put("batch.size", 65536);
props.put("linger.ms", 20);
props.put("compression.type", "lz4");
props.put("buffer.memory", 134217728); // 128MB

// Consumer optimizations  
props.put("fetch.min.bytes", 100000);
props.put("max.poll.records", 2000);
props.put("receive.buffer.bytes", 262144); // 256KB
```

3. **Infrastructure:**
   - SSD storage for brokers
   - Dedicated network for inter-broker communication
   - Separate clusters for different workloads

### Q18: Memory optimization for Kafka Streams
```java
// Reduce memory footprint
props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 10 * 1024 * 1024);
props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);

// Use RocksDB for large state
props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

// Custom RocksDB config
public class CustomRocksDBConfig implements RocksDBConfigSetter {
    @Override
    public void setConfig(String storeName, Options options, Map<String, Object> configs) {
        options.setWriteBufferSize(16 * 1024 * 1024); // 16MB
        options.setMaxWriteBufferNumber(3);
        options.setCompressionType(CompressionType.LZ4_COMPRESSION);
    }
}
```

## Quick Fire Questions

### Q19: What happens when ISR shrinks to 1?
**Answer:** Only leader remains in ISR. If `min.insync.replicas=2`, writes will fail with `NotEnoughReplicasException`.

### Q20: Difference between log compaction and retention?
**Answer:** 
- **Retention:** Deletes old messages by time/size
- **Compaction:** Keeps latest value per key, deletes old versions

### Q21: When to use KStream vs KTable?
**Answer:**
- **KStream:** Event stream, all records matter (clicks, transactions)
- **KTable:** Current state, only latest value matters (user profiles, configs)

### Q22: How to reset consumer group offset?
```bash
kafka-consumer-groups --bootstrap-server localhost:9092 \
  --group my-group --reset-offsets --to-earliest --topic my-topic --execute
```

### Q23: What triggers partition rebalance?
**Answer:** Consumer join/leave, heartbeat timeout, partition count change, subscription change.

### Q24: How to handle poison messages?
```java
stream.mapValues(value -> {
    try {
        return processValue(value);
    } catch (Exception e) {
        dlqProducer.send(new ProducerRecord<>("poison-messages", key, value));
        return null;
    }
}).filter((k, v) -> v != null);
```

### Q25: Explain co-partitioning requirement
**Answer:** For joins, topics must have same partition count and same partitioning strategy to ensure related data is on same partition across topics.

## Interview Preparation Tips

### Technical Depth Areas
1. **Hands-on coding** with Kafka APIs
2. **Performance tuning** real scenarios  
3. **Error handling** patterns
4. **System design** with Kafka
5. **Troubleshooting** production issues

### Key Talking Points
- **Trade-offs:** Throughput vs latency, consistency vs availability
- **Real experience:** Production challenges you've solved
- **Best practices:** Configuration, monitoring, testing
- **Scalability:** How you've scaled Kafka systems
- **Integration:** How Kafka fits in microservices architecture