# Kafka at Global Scale - Disney Streaming Scenarios

## Global Architecture Patterns

### Multi-Region Active-Active Setup
```java
// Region-aware producer
public class GlobalStreamingProducer {
    private final Map<String, KafkaProducer> regionalProducers;
    
    public void publishEvent(StreamingEvent event, String userRegion) {
        KafkaProducer producer = regionalProducers.get(userRegion);
        
        // Local region first, then replicate globally
        producer.send(new ProducerRecord<>("streaming-events-" + userRegion, event));
        
        // Cross-region replication for critical events
        if (event.isCritical()) {
            replicateToAllRegions(event);
        }
    }
}
```

### Geo-Partitioning Strategy
```java
public class GeoPartitioner implements Partitioner {
    private final Map<String, Integer> regionPartitions = Map.of(
        "US", 0, "EU", 1, "APAC", 2, "LATAM", 3
    );
    
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, 
                        Object value, byte[] valueBytes, Cluster cluster) {
        String region = extractRegion((String) key);
        int basePartition = regionPartitions.getOrDefault(region, 0);
        int partitionsPerRegion = cluster.partitionCountForTopic(topic) / 4;
        
        // Distribute within region partitions
        return basePartition * partitionsPerRegion + 
               (key.hashCode() % partitionsPerRegion);
    }
}
```

## High Concurrency Patterns

### 100M Concurrent Viewers Scenario
```java
public class MassiveScaleProcessor {
    // Partition by content + user segment for load distribution
    public String generatePartitionKey(String contentId, String userId) {
        String userSegment = getUserSegment(userId); // VIP, Premium, Free
        return contentId + "-" + userSegment + "-" + (userId.hashCode() % 1000);
    }
    
    // Async batch processing for viewer events
    public void processViewerEvents(List<ViewerEvent> events) {
        events.parallelStream()
              .collect(Collectors.groupingBy(this::getContentId))
              .entrySet()
              .parallelStream()
              .forEach(entry -> {
                  String contentId = entry.getKey();
                  List<ViewerEvent> contentEvents = entry.getValue();
                  
                  CompletableFuture.runAsync(() -> 
                      processContentViewers(contentId, contentEvents));
              });
    }
}
```

### Traffic Spike Handling (Marvel Movie Premiere)
```java
public class SpikeHandler {
    private final RateLimiter globalRateLimiter = RateLimiter.create(1_000_000); // 1M/sec
    private final LoadingCache<String, RateLimiter> contentRateLimiters;
    
    public boolean shouldProcessEvent(ViewerEvent event) {
        // Global rate limiting
        if (!globalRateLimiter.tryAcquire()) {
            return false;
        }
        
        // Per-content rate limiting for hot content
        RateLimiter contentLimiter = contentRateLimiters.get(event.getContentId());
        return contentLimiter.tryAcquire();
    }
    
    // Auto-scaling based on load
    public void autoScale(String contentId, double currentLoad) {
        if (currentLoad > 0.8) {
            scaleUpConsumers(contentId);
            increasePartitions(contentId);
        }
    }
}
```

## Real-Time Analytics at Scale

### Live Viewer Metrics (30-second windows)
```java
public class LiveAnalytics {
    public Topology buildLiveMetricsTopology() {
        StreamsBuilder builder = new StreamsBuilder();
        
        // Viewer events stream
        KStream<String, ViewerEvent> events = builder.stream("viewer-events");
        
        // Real-time concurrent viewers per content
        KTable<Windowed<String>, Long> concurrentViewers = events
            .filter((k, v) -> v.getEventType().equals("HEARTBEAT"))
            .groupBy((k, v) -> v.getContentId())
            .windowedBy(TimeWindows.of(Duration.ofSeconds(30)))
            .count(Materialized.as("concurrent-viewers-store"));
        
        // Geographic distribution
        KTable<Windowed<String>, Map<String, Long>> geoDistribution = events
            .groupBy((k, v) -> v.getContentId() + "#" + v.getRegion())
            .windowedBy(TimeWindows.of(Duration.ofMinutes(1)))
            .count()
            .groupBy((windowed, count) -> {
                String[] parts = windowed.key().split("#");
                return KeyValue.pair(parts[0], Map.of(parts[1], count));
            })
            .reduce((map1, map2) -> mergeMaps(map1, map2));
        
        return builder.build();
    }
}
```

### Content Recommendation Engine
```java
public class RealtimeRecommendations {
    public void buildRecommendationPipeline() {
        // User behavior stream
        KStream<String, UserBehavior> behavior = builder.stream("user-behavior");
        
        // Content similarity matrix (pre-computed)
        KTable<String, ContentSimilarity> similarities = builder.table("content-similarities");
        
        // Real-time recommendations
        KStream<String, Recommendation> recommendations = behavior
            .filter((k, v) -> v.getAction().equals("WATCH_COMPLETE"))
            .join(similarities, 
                (userBehavior, similarity) -> generateRecommendations(userBehavior, similarity))
            .flatMapValues(this::expandRecommendations);
        
        // Update user profiles
        recommendations.groupByKey()
            .aggregate(
                UserProfile::new,
                (userId, rec, profile) -> profile.addRecommendation(rec),
                Materialized.as("user-profiles")
            );
    }
}
```

## Performance Optimization

### Memory-Efficient Processing
```java
public class MemoryOptimizedProcessor {
    // Use off-heap storage for large state
    public StoreBuilder<KeyValueStore<String, ViewerSession>> buildStateStore() {
        return Stores.keyValueStoreBuilder(
            Stores.persistentKeyValueStore("viewer-sessions"),
            Serdes.String(),
            viewerSessionSerde
        ).withCachingEnabled()
         .withLoggingEnabled(Map.of(
             "cleanup.policy", "compact",
             "segment.ms", "3600000" // 1 hour segments
         ));
    }
    
    // Batch processing to reduce overhead
    private final List<ViewerEvent> eventBatch = new ArrayList<>(1000);
    
    public void processEvent(ViewerEvent event) {
        eventBatch.add(event);
        
        if (eventBatch.size() >= 1000) {
            processBatch(new ArrayList<>(eventBatch));
            eventBatch.clear();
        }
    }
}
```

### Network Optimization
```java
public class NetworkOptimizedProducer {
    public Properties getOptimizedConfig() {
        Properties props = new Properties();
        
        // Maximize throughput
        props.put("batch.size", 1048576);        // 1MB batches
        props.put("linger.ms", 100);             // 100ms batching window
        props.put("compression.type", "lz4");     // Fast compression
        props.put("buffer.memory", 134217728);    // 128MB buffer
        
        // Network tuning
        props.put("send.buffer.bytes", 1048576);  // 1MB send buffer
        props.put("receive.buffer.bytes", 1048576); // 1MB receive buffer
        
        return props;
    }
}
```

## Disaster Recovery & Resilience

### Multi-Region Failover
```java
public class DisasterRecovery {
    private final Map<String, KafkaProducer> producers;
    private volatile String primaryRegion = "us-east-1";
    
    public void handleRegionFailure(String failedRegion) {
        if (failedRegion.equals(primaryRegion)) {
            // Failover to backup region
            primaryRegion = determineBackupRegion(failedRegion);
            
            // Redirect traffic
            updateLoadBalancer(primaryRegion);
            
            // Resume processing from last checkpoint
            resumeFromCheckpoint(primaryRegion);
        }
    }
    
    public void send(ProducerRecord record) {
        try {
            producers.get(primaryRegion).send(record);
        } catch (Exception e) {
            // Automatic failover
            handleRegionFailure(primaryRegion);
            producers.get(primaryRegion).send(record);
        }
    }
}
```

### Circuit Breaker for External Services
```java
public class ResilientStreamProcessor {
    private final CircuitBreaker contentServiceBreaker = 
        CircuitBreaker.ofDefaults("content-service");
    
    public void processViewerEvent(ViewerEvent event) {
        // Enrich with content metadata (with circuit breaker)
        ContentMetadata metadata = contentServiceBreaker.executeSupplier(() -> 
            contentService.getMetadata(event.getContentId()));
        
        if (metadata != null) {
            // Full processing with metadata
            processWithMetadata(event, metadata);
        } else {
            // Degraded processing without metadata
            processBasicEvent(event);
        }
    }
}
```

## Monitoring at Scale

### Custom Metrics for Disney Streaming
```java
public class StreamingMetrics {
    // Business metrics
    public void trackContentPopularity(String contentId, String region) {
        Metrics.counter("content.views", 
            "content_id", contentId, 
            "region", region).increment();
    }
    
    public void trackUserEngagement(String userId, Duration watchTime) {
        Metrics.timer("user.engagement", 
            "user_tier", getUserTier(userId)).record(watchTime);
    }
    
    // Technical metrics
    public void trackProcessingLatency(Duration latency, String processor) {
        Metrics.timer("processing.latency", 
            "processor", processor).record(latency);
    }
    
    // Alert on anomalies
    public void checkAnomalies(String contentId, long currentViewers) {
        long historicalAverage = getHistoricalAverage(contentId);
        
        if (currentViewers > historicalAverage * 10) {
            alertManager.sendAlert("Viral content detected: " + contentId);
        }
    }
}
```

## Interview Scenarios

### Q1: Design system for 100M users watching Avengers premiere simultaneously
**Key Points:**
- Geographic load distribution
- Auto-scaling based on demand
- Circuit breakers for external services
- Real-time metrics and alerting

### Q2: Handle data consistency across regions for user preferences
**Solution:**
- Eventually consistent model
- Conflict resolution strategies
- Local caching with TTL
- Cross-region replication patterns

### Q3: Optimize costs while maintaining performance during low-traffic periods
**Approach:**
- Auto-scaling down during off-peak
- Spot instances for batch processing
- Tiered storage (hot/warm/cold)
- Compression and data lifecycle policies