# Kafka + AWS Integration for Streaming Platforms

## AWS MSK (Managed Streaming for Kafka)

### MSK vs Self-Managed Kafka
```java
// MSK Connection
props.put("bootstrap.servers", "b-1.mycluster.kafka.us-east-1.amazonaws.com:9092");
props.put("security.protocol", "SSL");
props.put("ssl.truststore.location", "/tmp/kafka.client.truststore.jks");

// IAM Authentication
props.put("security.protocol", "SASL_SSL");
props.put("sasl.mechanism", "AWS_MSK_IAM");
props.put("sasl.jaas.config", "software.amazon.msk.auth.iam.IAMLoginModule required;");
```

### MSK Connect for Data Integration
```json
{
  "connectorConfiguration": {
    "connector.class": "io.confluent.connect.s3.S3SinkConnector",
    "tasks.max": "2",
    "topics": "streaming-events",
    "s3.bucket.name": "disney-streaming-data",
    "s3.part.size": "5242880",
    "flush.size": "1000",
    "storage.class": "io.confluent.connect.s3.storage.S3Storage",
    "format.class": "io.confluent.connect.s3.format.avro.AvroFormat"
  }
}
```

## Multi-Service Architecture

### Kafka + Kinesis + SQS Integration
```java
// Event routing based on priority
public class EventRouter {
    public void routeEvent(StreamingEvent event) {
        switch (event.getPriority()) {
            case REAL_TIME:
                // Live streaming events → Kinesis
                kinesisProducer.addUserRecord("live-stream", event.getUserId(), event.toJson());
                break;
            case BATCH:
                // VOD analytics → Kafka
                kafkaProducer.send(new ProducerRecord<>("vod-analytics", event));
                break;
            case NOTIFICATION:
                // User notifications → SQS
                sqsClient.sendMessage("user-notifications", event.toJson());
                break;
        }
    }
}
```

### Cross-Region Replication
```java
// Multi-region setup for global streaming
props.put("bootstrap.servers", 
    "us-east-1.kafka.amazonaws.com:9092,eu-west-1.kafka.amazonaws.com:9092");

// MirrorMaker 2.0 for cross-region sync
public class GlobalStreamingReplication {
    public void setupReplication() {
        Map<String, String> mm2Config = Map.of(
            "clusters", "us-east-1, eu-west-1, ap-southeast-1",
            "us-east-1.bootstrap.servers", "us-east-1-cluster:9092",
            "eu-west-1.bootstrap.servers", "eu-west-1-cluster:9092",
            "replication.policy.class", "org.apache.kafka.connect.mirror.DefaultReplicationPolicy"
        );
    }
}
```

## Streaming Data Pipeline

### Live Streaming Events
```java
// Real-time viewer analytics
KStream<String, ViewerEvent> viewerEvents = builder.stream("viewer-events");

// Windowed aggregations for live metrics
KTable<Windowed<String>, ViewerMetrics> liveMetrics = viewerEvents
    .groupByKey()
    .windowedBy(TimeWindows.of(Duration.ofSeconds(30)))
    .aggregate(
        ViewerMetrics::new,
        (key, event, metrics) -> metrics.addEvent(event),
        Materialized.as("live-metrics-store")
    );

// Push to CloudWatch for monitoring
liveMetrics.toStream()
    .foreach((window, metrics) -> 
        cloudWatchClient.putMetricData(metrics.toCloudWatchData()));
```

### VOD Content Processing
```java
// Content ingestion pipeline
KStream<String, ContentEvent> contentEvents = builder.stream("content-ingestion");

// Transform and enrich content metadata
KStream<String, EnrichedContent> enrichedContent = contentEvents
    .join(contentMetadataTable, this::enrichWithMetadata)
    .mapValues(this::addThumbnails)
    .mapValues(this::generateSubtitles);

// Route to different destinations
enrichedContent.split()
    .branch((k, v) -> v.getContentType().equals("MOVIE"), 
            Branched.withConsumer(s -> s.to("movie-catalog")))
    .branch((k, v) -> v.getContentType().equals("SERIES"),
            Branched.withConsumer(s -> s.to("series-catalog")))
    .defaultBranch(Branched.withConsumer(s -> s.to("other-content")));
```

## High Concurrency Patterns

### Async Processing with CompletableFuture
```java
public class AsyncStreamProcessor {
    private final ExecutorService executor = ForkJoinPool.commonPool();
    
    public CompletableFuture<Void> processStreamingData(List<StreamingEvent> events) {
        return CompletableFuture.allOf(
            events.stream()
                .map(event -> CompletableFuture
                    .supplyAsync(() -> processEvent(event), executor)
                    .thenCompose(this::enrichEvent)
                    .thenAccept(this::publishEvent))
                .toArray(CompletableFuture[]::new)
        );
    }
}
```

### Backpressure Handling
```java
public class BackpressureHandler {
    private final Semaphore semaphore = new Semaphore(1000);
    
    public void handleStreamingLoad(StreamingEvent event) {
        try {
            semaphore.acquire();
            processEvent(event);
        } catch (InterruptedException e) {
            // Queue for later processing
            fallbackQueue.offer(event);
        } finally {
            semaphore.release();
        }
    }
}
```

## AWS Infrastructure Integration

### CloudFormation for Kafka Infrastructure
```yaml
MSKCluster:
  Type: AWS::MSK::Cluster
  Properties:
    ClusterName: disney-streaming-kafka
    KafkaVersion: 2.8.1
    NumberOfBrokerNodes: 6
    BrokerNodeGroupInfo:
      InstanceType: kafka.m5.2xlarge
      ClientSubnets: [subnet-1, subnet-2, subnet-3]
      SecurityGroups: [sg-kafka]
      StorageInfo:
        EBSStorageInfo:
          VolumeSize: 1000
    EncryptionInfo:
      EncryptionInTransit:
        ClientBroker: TLS
        InCluster: true
```

### Lambda Integration for Serverless Processing
```java
public class KafkaLambdaProcessor implements RequestHandler<KafkaEvent, String> {
    @Override
    public String handleRequest(KafkaEvent event, Context context) {
        event.getRecords().forEach((partition, records) -> {
            records.forEach(record -> {
                StreamingEvent streamingEvent = deserialize(record.getValue());
                
                // Process streaming analytics
                if (streamingEvent.getType().equals("VIEW_START")) {
                    updateViewerMetrics(streamingEvent);
                } else if (streamingEvent.getType().equals("CONTENT_COMPLETE")) {
                    updateContentMetrics(streamingEvent);
                }
            });
        });
        return "Processed " + event.getRecords().size() + " partitions";
    }
}
```

## Monitoring & Observability

### CloudWatch Integration
```java
public class StreamingMetrics {
    private final CloudWatchAsyncClient cloudWatch;
    
    public void publishMetrics(String metricName, double value, String contentId) {
        PutMetricDataRequest request = PutMetricDataRequest.builder()
            .namespace("Disney/Streaming")
            .metricData(MetricDatum.builder()
                .metricName(metricName)
                .value(value)
                .dimensions(Dimension.builder()
                    .name("ContentId")
                    .value(contentId)
                    .build())
                .timestamp(Instant.now())
                .build())
            .build();
            
        cloudWatch.putMetricData(request);
    }
}
```

### X-Ray Tracing
```java
@XRayEnabled
public class TracedStreamProcessor {
    @Trace
    public void processStreamingEvent(StreamingEvent event) {
        AWSXRay.createSubsegment("kafka-processing", (subsegment) -> {
            subsegment.putAnnotation("eventType", event.getType());
            subsegment.putAnnotation("contentId", event.getContentId());
            
            // Process event
            processEvent(event);
        });
    }
}
```

## Simplified Interview Approach

### Basic System Design (Learn This Pattern)
```
Users → API Gateway → Kafka (partitioned by userID) → Stream Processing → Database
                        ↓
                   CloudWatch Monitoring
```

### Key Talking Points
1. **Why Kafka**: High throughput, durability, scalability
2. **Partitioning**: Hash user ID for even distribution
3. **Scaling**: Add partitions + consumer instances
4. **Monitoring**: Consumer lag, throughput, error rates
5. **Error Handling**: Retry logic + dead letter queues

### Safe Interview Answers

**Q: "Design streaming analytics system"**
**A:** "I'd use Kafka as the event backbone, partition by user ID, process with Kafka Streams for real-time metrics, and monitor consumer lag for performance."

**Q: "Handle traffic spikes"**
**A:** "Implement rate limiting, circuit breakers for external calls, auto-scale consumers based on lag metrics, and use caching for hot data."

**Q: "AWS services choice"**
**A:** "SQS for simple queues, Kinesis for AWS-native analytics, Kafka/MSK for complex stream processing with high throughput requirements."

### What to Focus On (2-3 Days Learning)
- Basic Kafka producer/consumer patterns
- Simple partitioning strategies
- Rate limiting and circuit breaker concepts
- CloudWatch monitoring basics
- Dead letter queue error handling