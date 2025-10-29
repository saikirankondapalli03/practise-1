# Flink Hands-On Practice - Interview Preparation

## Quick Setup for Demo (If Asked to Code)

### Maven Dependencies (Just Know These Exist)
```xml
<dependencies>
    <dependency>
        <groupId>org.apache.flink</groupId>
        <artifactId>flink-streaming-java_2.12</artifactId>
        <version>1.17.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.flink</groupId>
        <artifactId>flink-connector-kafka</artifactId>
        <version>1.17.0</version>
    </dependency>
</dependencies>
```

## Interview-Ready Code Examples

### 1. Basic Event Processing (Your Go-To Example)
```java
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

public class UserEventProcessor {
    public static void main(String[] args) throws Exception {
        // Setup environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(5000); // Checkpoint every 5 seconds
        
        // Kafka source (leverage your Kafka knowledge)
        Properties kafkaProps = new Properties();
        kafkaProps.setProperty("bootstrap.servers", "localhost:9092");
        kafkaProps.setProperty("group.id", "flink-processor");
        
        DataStream<UserEvent> events = env.addSource(
            new FlinkKafkaConsumer<>("user-events", new UserEventDeserializer(), kafkaProps)
        );
        
        // Processing pipeline (similar to Spark transformations)
        DataStream<ProcessedEvent> processed = events
            .filter(event -> event.getEventType().equals("VIEW")) // Filter like Spark
            .map(new EventEnricher()) // Transform like Spark
            .keyBy(UserEvent::getUserId) // Group by key like Spark
            .window(TumblingProcessingTimeWindows.of(Time.minutes(5))) // Window operations
            .aggregate(new ViewCountAggregator()); // Reduce operation
        
        // Output
        processed.print(); // For demo
        // processed.addSink(new FlinkKafkaProducer<>(...)); // Real implementation
        
        env.execute("User Event Processor");
    }
}

// Supporting classes (just show structure)
class UserEvent {
    private String userId;
    private String eventType;
    private long timestamp;
    // getters/setters
}

class ProcessedEvent {
    private String userId;
    private long viewCount;
    private long windowStart;
    // getters/setters
}
```

### 2. Real-Time Analytics Dashboard (Interview Favorite)
```java
public class RealTimeAnalytics {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // Multiple data sources (show integration skills)
        DataStream<UserEvent> userEvents = env.addSource(new KafkaUserEventSource());
        DataStream<ContentEvent> contentEvents = env.addSource(new KafkaContentEventSource());
        
        // Real-time metrics computation
        DataStream<UserMetrics> userMetrics = userEvents
            .keyBy(UserEvent::getUserId)
            .window(SlidingProcessingTimeWindows.of(Time.minutes(10), Time.minutes(1)))
            .aggregate(new UserMetricsAggregator());
        
        DataStream<ContentMetrics> contentMetrics = contentEvents
            .keyBy(ContentEvent::getContentId)
            .window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
            .aggregate(new ContentMetricsAggregator());
        
        // Join streams (advanced operation)
        DataStream<EnrichedMetrics> enriched = userMetrics
            .connect(contentMetrics)
            .process(new MetricsJoiner());
        
        // Multiple outputs (side outputs pattern)
        SingleOutputStreamOperator<DashboardUpdate> dashboardUpdates = enriched
            .process(new DashboardProcessor());
        
        // Send to different sinks
        dashboardUpdates.addSink(new ElasticsearchSink<>(...)); // Real-time dashboard
        dashboardUpdates.getSideOutput(alertTag).addSink(new AlertSink()); // Alerts
        
        env.execute("Real-Time Analytics");
    }
}
```

### 3. Fraud Detection System (Complex Event Processing)
```java
public class FraudDetectionSystem {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        DataStream<Transaction> transactions = env.addSource(new TransactionSource());
        
        // Pattern detection (CEP - Complex Event Processing)
        DataStream<FraudAlert> alerts = transactions
            .keyBy(Transaction::getUserId)
            .process(new FraudDetectionFunction());
        
        // High-priority alerts
        DataStream<FraudAlert> criticalAlerts = alerts
            .filter(alert -> alert.getRiskScore() > 0.9);
        
        criticalAlerts.addSink(new AlertingSink());
        
        env.execute("Fraud Detection");
    }
}

// Stateful fraud detection function
class FraudDetectionFunction extends KeyedProcessFunction<String, Transaction, FraudAlert> {
    
    // State to track user behavior
    private ValueState<UserProfile> userProfileState;
    private ListState<Transaction> recentTransactionsState;
    
    @Override
    public void open(Configuration parameters) {
        // Initialize state
        userProfileState = getRuntimeContext().getState(
            new ValueStateDescriptor<>("userProfile", UserProfile.class)
        );
        recentTransactionsState = getRuntimeContext().getListState(
            new ListStateDescriptor<>("recentTransactions", Transaction.class)
        );
    }
    
    @Override
    public void processElement(Transaction transaction, Context ctx, Collector<FraudAlert> out) 
            throws Exception {
        
        UserProfile profile = userProfileState.value();
        if (profile == null) {
            profile = new UserProfile();
        }
        
        // Add current transaction to recent list
        recentTransactionsState.add(transaction);
        
        // Fraud detection logic
        double riskScore = calculateRiskScore(transaction, profile, recentTransactionsState.get());
        
        if (riskScore > 0.7) {
            out.collect(new FraudAlert(transaction.getUserId(), riskScore, transaction));
        }
        
        // Update profile
        profile.updateWith(transaction);
        userProfileState.update(profile);
        
        // Clean old transactions (timer-based cleanup)
        ctx.timerService().registerProcessingTimeTimer(
            ctx.timerService().currentProcessingTime() + 3600000 // 1 hour
        );
    }
    
    private double calculateRiskScore(Transaction txn, UserProfile profile, 
                                    Iterable<Transaction> recent) {
        // Simple risk calculation
        double score = 0.0;
        
        // Check amount vs historical average
        if (txn.getAmount() > profile.getAverageAmount() * 3) {
            score += 0.4;
        }
        
        // Check location
        if (!txn.getLocation().equals(profile.getUsualLocation())) {
            score += 0.3;
        }
        
        // Check frequency
        long recentCount = StreamSupport.stream(recent.spliterator(), false).count();
        if (recentCount > 10) { // More than 10 transactions in window
            score += 0.3;
        }
        
        return Math.min(score, 1.0);
    }
}
```

## Common Patterns You Should Know

### 1. Windowing Patterns
```java
// Time-based windows
stream.keyBy(Event::getKey)
      .window(TumblingProcessingTimeWindows.of(Time.minutes(5))) // Non-overlapping
      .sum("value");

stream.keyBy(Event::getKey)
      .window(SlidingProcessingTimeWindows.of(Time.minutes(10), Time.minutes(2))) // Overlapping
      .sum("value");

// Count-based windows
stream.keyBy(Event::getKey)
      .countWindow(100) // Every 100 events
      .sum("value");

// Session windows (user activity based)
stream.keyBy(Event::getUserId)
      .window(ProcessingTimeSessionWindows.withGap(Time.minutes(30)))
      .sum("value");
```

### 2. State Management Patterns
```java
// Value state (single value per key)
private ValueState<Long> countState;

// List state (list of values per key)
private ListState<String> itemsState;

// Map state (key-value pairs per key)
private MapState<String, Long> countersState;

// Usage in process function
@Override
public void processElement(Event event, Context ctx, Collector<Result> out) throws Exception {
    // Get current count
    Long currentCount = countState.value();
    if (currentCount == null) currentCount = 0L;
    
    // Update count
    countState.update(currentCount + 1);
    
    // Emit result
    out.collect(new Result(event.getKey(), currentCount + 1));
}
```

### 3. Error Handling Patterns
```java
// Side outputs for error handling
final OutputTag<ErrorEvent> errorTag = new OutputTag<ErrorEvent>("errors"){};

SingleOutputStreamOperator<ProcessedEvent> processed = events
    .process(new ProcessFunction<Event, ProcessedEvent>() {
        @Override
        public void processElement(Event event, Context ctx, Collector<ProcessedEvent> out) {
            try {
                ProcessedEvent result = processEvent(event);
                out.collect(result);
            } catch (Exception e) {
                ctx.output(errorTag, new ErrorEvent(event, e.getMessage()));
            }
        }
    });

// Handle errors separately
DataStream<ErrorEvent> errors = processed.getSideOutput(errorTag);
errors.addSink(new ErrorSink());
```

## AWS Kinesis Analytics Integration

### Flink on AWS (Your Cloud Story)
```java
// Kinesis Data Analytics for Apache Flink
public class KinesisFlinkJob {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // Kinesis source (instead of Kafka)
        DataStream<String> input = env.addSource(
            new FlinkKinesisConsumer<>("input-stream", new SimpleStringSchema(), kinesisConfig)
        );
        
        // Processing (same as before)
        DataStream<ProcessedEvent> processed = input
            .map(new EventParser())
            .keyBy(Event::getPartitionKey)
            .window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
            .aggregate(new EventAggregator());
        
        // Multiple outputs
        processed.addSink(new FlinkKinesisProducer<>("output-stream", kinesisConfig));
        processed.addSink(new ElasticsearchSink<>(esConfig));
        
        env.execute("Kinesis Flink Job");
    }
}
```

## Performance and Scaling Considerations

### Parallelism Configuration
```java
// Set parallelism
env.setParallelism(4); // 4 parallel instances

// Per operator parallelism
stream.map(new MyMapper()).setParallelism(8);

// Disable chaining for better monitoring
stream.map(new MyMapper()).disableChaining();
```

### Checkpointing Configuration
```java
// Enable checkpointing
env.enableCheckpointing(5000); // Every 5 seconds

// Checkpoint configuration
CheckpointConfig config = env.getCheckpointConfig();
config.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
config.setMinPauseBetweenCheckpoints(500);
config.setCheckpointTimeout(60000);
config.setMaxConcurrentCheckpoints(1);

// State backend
env.setStateBackend(new HashMapStateBackend());
env.getCheckpointConfig().setCheckpointStorage("s3://my-bucket/checkpoints");
```

## Monitoring and Metrics (Interview Topic)

### Key Metrics to Monitor
```java
// Custom metrics
public class MyProcessFunction extends ProcessFunction<Event, Result> {
    private Counter eventCounter;
    private Histogram processingLatency;
    
    @Override
    public void open(Configuration parameters) {
        eventCounter = getRuntimeContext()
            .getMetricGroup()
            .counter("events_processed");
            
        processingLatency = getRuntimeContext()
            .getMetricGroup()
            .histogram("processing_latency", new DescriptiveStatisticsHistogram(1000));
    }
    
    @Override
    public void processElement(Event event, Context ctx, Collector<Result> out) {
        long startTime = System.currentTimeMillis();
        
        // Process event
        Result result = processEvent(event);
        out.collect(result);
        
        // Update metrics
        eventCounter.inc();
        processingLatency.update(System.currentTimeMillis() - startTime);
    }
}
```

## Interview Questions & Your Answers

### Q: "How would you optimize a slow Flink job?"
**Your Approach:**
```
1. Check parallelism: Increase if CPU utilization is low
2. Monitor checkpointing: Reduce frequency if it's causing backpressure
3. Optimize serialization: Use Avro/Protobuf instead of Java serialization
4. Tune memory: Adjust taskmanager memory settings
5. Check data skew: Ensure even key distribution
6. Monitor GC: Tune JVM garbage collection settings
```

### Q: "How do you handle late arriving data?"
**Your Answer:**
```java
// Event time processing with watermarks
env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

DataStream<Event> events = source
    .assignTimestampsAndWatermarks(
        WatermarkStrategy.<Event>forBoundedOutOfOrderness(Duration.ofMinutes(5))
            .withTimestampAssigner((event, timestamp) -> event.getEventTime())
    );

// Window with allowed lateness
events.keyBy(Event::getKey)
      .window(TumblingEventTimeWindows.of(Time.minutes(10)))
      .allowedLateness(Time.minutes(2)) // Accept late data for 2 minutes
      .sideOutputLateData(lateDataTag) // Handle very late data
      .sum("value");
```

## Practice Scenarios for Interview

### Scenario 1: Real-time Recommendation System
```java
// User clicks stream
DataStream<ClickEvent> clicks = env.addSource(clickSource);

// Real-time user profile updates
DataStream<UserProfile> profiles = clicks
    .keyBy(ClickEvent::getUserId)
    .process(new UserProfileUpdater()); // Stateful processing

// Generate recommendations
DataStream<Recommendation> recommendations = profiles
    .connect(contentCatalog)
    .process(new RecommendationGenerator());
```

### Scenario 2: IoT Sensor Monitoring
```java
// Sensor data stream
DataStream<SensorReading> sensors = env.addSource(sensorSource);

// Detect anomalies
DataStream<Alert> alerts = sensors
    .keyBy(SensorReading::getSensorId)
    .process(new AnomalyDetector()); // CEP for pattern detection

// Aggregate for dashboard
DataStream<SensorStats> stats = sensors
    .keyBy(SensorReading::getSensorId)
    .window(TumblingProcessingTimeWindows.of(Time.minutes(1)))
    .aggregate(new SensorStatsAggregator());
```

## Your 3-Day Learning Plan

### Day 1: Core API
- Set up basic Flink job with Kafka source/sink
- Practice DataStream transformations
- Implement simple windowing operations
- Test with sample data

### Day 2: State and Time
- Implement stateful processing function
- Practice event time vs processing time
- Add watermarks for late data handling
- Implement checkpointing

### Day 3: Advanced Patterns
- Build fraud detection example
- Practice side outputs for error handling
- Implement custom metrics
- Test on AWS Kinesis Analytics (if possible)

## Key Takeaways for Interview

**Your Positioning:**
- "I'm leveraging my Kafka and Spark experience to learn Flink"
- "Flink bridges the gap between Kafka Streams and Spark Streaming"
- "I'm particularly interested in Flink's exactly-once guarantees and low latency"

**Technical Confidence:**
- Focus on concepts you understand (windowing, state, checkpointing)
- Relate everything back to Kafka/Spark equivalents
- Show learning progression, not false expertise
- Emphasize practical use cases over theoretical knowledge

This hands-on approach will give you concrete examples to discuss in interviews while building on your existing streaming knowledge.