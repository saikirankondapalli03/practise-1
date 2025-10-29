# Redis in System Design - Architecture Patterns

## Redis Integration Patterns (Interview Favorites)

### 1. Cache-Aside Pattern (Most Common)
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│ Application │───▶│    Redis    │    │  Database   │
│             │    │   (Cache)   │    │ (Primary)   │
└─────────────┘    └─────────────┘    └─────────────┘
       │                   │                   │
       └───────────────────┼───────────────────┘
                           │
                    Cache Miss Flow:
                    1. Check Redis
                    2. If miss, query DB
                    3. Store in Redis
                    4. Return data

Flow Diagram:
Client Request → App checks Redis → Cache Hit? → Return data
                      │                 │
                      ▼ (Cache Miss)    │
                 Query Database ────────┘
                      │
                      ▼
                 Store in Redis
                      │
                      ▼
                 Return data
```

**Interview Code Example:**
```python
def get_user_profile(user_id):
    # 1. Check cache first
    cache_key = f"user:profile:{user_id}"
    cached_data = redis.get(cache_key)
    
    if cached_data:
        return json.loads(cached_data)  # Cache hit
    
    # 2. Cache miss - query database
    user_data = database.query("SELECT * FROM users WHERE id = %s", user_id)
    
    # 3. Store in cache for future requests
    redis.setex(cache_key, 3600, json.dumps(user_data))  # 1 hour TTL
    
    return user_data
```

**Your Interview Answer:**
> "Cache-aside is the most common pattern. The application manages the cache explicitly - checking Redis first, then falling back to the database on cache misses. It's simple and gives you full control over what gets cached."

### 2. Write-Through Pattern
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│ Application │───▶│    Redis    │───▶│  Database   │
│             │    │   (Cache)   │    │ (Primary)   │
└─────────────┘    └─────────────┘    └─────────────┘

Write Flow:
1. App writes to Redis
2. Redis writes to Database
3. Confirm to App

Read Flow:
1. App reads from Redis (always up-to-date)
```

**Interview Code Example:**
```python
def update_user_profile(user_id, profile_data):
    cache_key = f"user:profile:{user_id}"
    
    # Write to cache first
    redis.setex(cache_key, 3600, json.dumps(profile_data))
    
    # Then write to database
    database.update("UPDATE users SET ... WHERE id = %s", profile_data, user_id)
    
    # Cache is always consistent with database
```

### 3. Write-Behind (Write-Back) Pattern
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│ Application │───▶│    Redis    │~~~▶│  Database   │
│             │    │   (Cache)   │    │ (Primary)   │
└─────────────┘    └─────────────┘    └─────────────┘
                          │                   │
                          └── Async Write ────┘

Write Flow:
1. App writes to Redis (fast)
2. Redis asynchronously writes to DB
3. Immediate response to App

Benefits: Very fast writes
Risks: Potential data loss if Redis fails
```

### 4. Session Store Pattern
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│ Load        │    │   App       │    │   App       │
│ Balancer    │    │ Server 1    │    │ Server 2    │
└─────────────┘    └─────────────┘    └─────────────┘
       │                   │                   │
       └───────────────────┼───────────────────┘
                           │
                           ▼
                    ┌─────────────┐
                    │    Redis    │
                    │ (Sessions)  │
                    └─────────────┘

Benefits:
- Stateless application servers
- Session sharing across servers
- Fast session access
- Automatic expiration
```

**Interview Code Example:**
```python
# Microservices sharing sessions
class SessionStore:
    def create_session(self, user_id, session_data):
        session_id = str(uuid.uuid4())
        session_key = f"session:{session_id}"
        
        # Store with 24-hour expiration
        redis.hset(session_key, mapping={
            "user_id": user_id,
            "created_at": int(time.time()),
            **session_data
        })
        redis.expire(session_key, 86400)
        
        return session_id
    
    def validate_session(self, session_id):
        session_key = f"session:{session_id}"
        session_data = redis.hgetall(session_key)
        
        if session_data:
            # Extend session on activity
            redis.expire(session_key, 86400)
            return session_data
        
        return None
```

## System Architecture Patterns

### 1. Microservices with Redis
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   User      │    │   Order     │    │  Inventory  │
│  Service    │    │  Service    │    │   Service   │
└─────────────┘    └─────────────┘    └─────────────┘
       │                   │                   │
       └───────────────────┼───────────────────┘
                           │
                           ▼
                    ┌─────────────┐
                    │    Redis    │
                    │  (Shared)   │
                    └─────────────┘

Use Cases:
- Shared cache across services
- Inter-service communication (Pub/Sub)
- Distributed locks
- Rate limiting
- Session management
```

### 2. Event-Driven Architecture with Redis Streams
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  Producer   │───▶│   Redis     │───▶│  Consumer   │
│  Service    │    │  Streams    │    │   Group     │
└─────────────┘    └─────────────┘    └─────────────┘
                          │
                          ├───▶ Consumer 1
                          ├───▶ Consumer 2
                          └───▶ Consumer 3

Benefits:
- Message persistence
- Consumer groups
- Message acknowledgment
- Replay capability
```

**Interview Code Example:**
```python
# Event streaming with Redis
def publish_event(stream_name, event_data):
    """Publish event to Redis stream"""
    redis.xadd(stream_name, event_data)

def consume_events(stream_name, consumer_group, consumer_name):
    """Consume events from Redis stream"""
    try:
        # Create consumer group if not exists
        redis.xgroup_create(stream_name, consumer_group, id='0', mkstream=True)
    except:
        pass  # Group already exists
    
    while True:
        # Read new messages
        messages = redis.xreadgroup(
            consumer_group,
            consumer_name,
            {stream_name: '>'},
            count=10,
            block=1000
        )
        
        for stream, msgs in messages:
            for msg_id, fields in msgs:
                try:
                    # Process message
                    process_event(fields)
                    
                    # Acknowledge message
                    redis.xack(stream_name, consumer_group, msg_id)
                except Exception as e:
                    print(f"Error processing message {msg_id}: {e}")
```

### 3. Real-time Analytics Pipeline
```
Data Sources → Redis Streams → Processing → Redis Cache → Dashboard
     │              │              │            │           │
   Events      Event Buffer    Analytics    Results     Real-time
  (Users)      (Temporary)     (Compute)    (Fast)      Display

Components:
1. Redis Streams: Buffer incoming events
2. Processing: Compute metrics, aggregations
3. Redis Cache: Store computed results
4. Dashboard: Fast data retrieval
```

### 4. Gaming Leaderboard System
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Game      │───▶│   Redis     │───▶│  Dashboard  │
│  Servers    │    │ Sorted Sets │    │             │
└─────────────┘    └─────────────┘    └─────────────┘
       │                   │                   │
   Score Updates      Real-time         Live Rankings
   (High Volume)      Rankings          (Sub-second)

Features:
- Real-time score updates
- Top N players
- Player rank lookup
- Score range queries
- Multiple leaderboards
```

## Performance Optimization Patterns

### 1. Redis Cluster for Scale
```
Application Layer:
┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│   App 1     │  │   App 2     │  │   App 3     │
└─────────────┘  └─────────────┘  └─────────────┘
       │                │                │
       └────────────────┼────────────────┘
                        │
Redis Cluster:
┌─────────────────────────────────────────────────────┐
│ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐     │
│ │   Master 1  │ │   Master 2  │ │   Master 3  │     │
│ │ Slots:      │ │ Slots:      │ │ Slots:      │     │
│ │ 0-5460      │ │ 5461-10922  │ │ 10923-16383 │     │
│ └─────────────┘ └─────────────┘ └─────────────┘     │
│        │               │               │            │
│ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐     │
│ │  Replica 1  │ │  Replica 2  │ │  Replica 3  │     │
│ └─────────────┘ └─────────────┘ └─────────────┘     │
└─────────────────────────────────────────────────────┘

Benefits:
- Horizontal scaling
- Automatic sharding
- High availability
- Failover handling
```

### 2. Read Replicas Pattern
```
┌─────────────┐    ┌─────────────┐
│ Application │───▶│   Master    │ ← Writes
│             │    │   Redis     │
└─────────────┘    └─────────────┘
       │                   │
       │            Replication
       │                   │
       │                   ▼
       │            ┌─────────────┐
       └───────────▶│  Replica 1  │ ← Reads
                    └─────────────┘
                           │
                    ┌─────────────┐
                    │  Replica 2  │ ← Reads
                    └─────────────┘

Use Case: Read-heavy workloads
```

### 3. Multi-Layer Caching
```
┌─────────────┐
│ Application │
└─────────────┘
       │
       ▼
┌─────────────┐ ← L1 Cache (Application Memory)
│ Local Cache │   - Fastest access
└─────────────┘   - Limited size
       │
       ▼
┌─────────────┐ ← L2 Cache (Redis)
│    Redis    │   - Shared across instances
└─────────────┘   - Larger capacity
       │
       ▼
┌─────────────┐ ← L3 Storage (Database)
│  Database   │   - Persistent storage
└─────────────┘   - Slowest access
```

## Common System Design Interview Questions

### Q1: "Design a URL shortener like bit.ly"
**Your Redis Architecture:**
```
Components:
1. URL Mapping: Redis Hash
   - Key: short_code
   - Value: {original_url, created_at, expires_at}

2. Analytics: Redis Sorted Set
   - Key: clicks:{short_code}
   - Score: timestamp, Member: user_info

3. Rate Limiting: Redis Sorted Set
   - Key: rate_limit:{user_id}
   - Sliding window implementation

4. Cache: Redis String
   - Key: url_cache:{short_code}
   - Value: original_url (for fast redirects)

Code Structure:
```python
class URLShortener:
    def create_short_url(self, long_url, user_id):
        # Check rate limit
        if self.is_rate_limited(user_id):
            raise RateLimitError()
        
        # Generate short code
        short_code = self.generate_code()
        
        # Store mapping
        redis.hset(f"url:{short_code}", mapping={
            "original_url": long_url,
            "created_at": int(time.time()),
            "user_id": user_id
        })
        
        # Cache for fast access
        redis.setex(f"cache:{short_code}", 3600, long_url)
        
        return short_code
    
    def redirect(self, short_code):
        # Try cache first
        url = redis.get(f"cache:{short_code}")
        if url:
            self.track_click(short_code)
            return url
        
        # Fallback to main storage
        url_data = redis.hgetall(f"url:{short_code}")
        if url_data:
            # Refresh cache
            redis.setex(f"cache:{short_code}", 3600, url_data["original_url"])
            self.track_click(short_code)
            return url_data["original_url"]
        
        return None
```

### Q2: "Design a chat system"
**Your Redis Architecture:**
```
Components:
1. Message Storage: Redis Lists
   - Key: chat:{room_id}
   - Value: JSON message objects

2. Online Users: Redis Sets
   - Key: online:{room_id}
   - Members: user_ids

3. User Presence: Redis Hash
   - Key: presence:{user_id}
   - Fields: {room_id, last_seen, status}

4. Real-time Messaging: Redis Pub/Sub
   - Channel: room:{room_id}
   - Messages: Real-time delivery

Architecture:
```python
class ChatSystem:
    def send_message(self, room_id, user_id, message):
        message_data = {
            "id": str(uuid.uuid4()),
            "user_id": user_id,
            "message": message,
            "timestamp": time.time()
        }
        
        # Store message (keep last 1000)
        redis.lpush(f"chat:{room_id}", json.dumps(message_data))
        redis.ltrim(f"chat:{room_id}", 0, 999)
        
        # Publish to online users
        redis.publish(f"room:{room_id}", json.dumps(message_data))
        
        # Update user presence
        redis.hset(f"presence:{user_id}", "last_activity", int(time.time()))
    
    def join_room(self, room_id, user_id):
        # Add to online users
        redis.sadd(f"online:{room_id}", user_id)
        
        # Update presence
        redis.hset(f"presence:{user_id}", mapping={
            "room_id": room_id,
            "status": "online",
            "joined_at": int(time.time())
        })
        
        # Notify others
        redis.publish(f"room:{room_id}", json.dumps({
            "type": "user_joined",
            "user_id": user_id
        }))
```

### Q3: "Design a rate limiter"
**Your Redis Architecture:**
```
Algorithms:
1. Token Bucket: Redis Hash + Lua Script
2. Sliding Window: Redis Sorted Set
3. Fixed Window: Redis String with expiration

Implementation:
```python
class RateLimiter:
    def sliding_window_limiter(self, user_id, limit, window_seconds):
        """Sliding window using sorted set"""
        key = f"rate_limit:{user_id}"
        current_time = time.time()
        
        # Lua script for atomic operations
        lua_script = """
        local key = KEYS[1]
        local window = tonumber(ARGV[1])
        local limit = tonumber(ARGV[2])
        local current_time = tonumber(ARGV[3])
        
        -- Remove old entries
        redis.call('ZREMRANGEBYSCORE', key, 0, current_time - window)
        
        -- Count current requests
        local current_count = redis.call('ZCARD', key)
        
        if current_count < limit then
            -- Add current request
            redis.call('ZADD', key, current_time, current_time)
            redis.call('EXPIRE', key, window)
            return {1, limit - current_count - 1}
        else
            return {0, 0}
        end
        """
        
        result = redis.eval(lua_script, 1, key, window_seconds, limit, current_time)
        return result[0] == 1, result[1]  # allowed, remaining
```

## Your Interview Strategy

### When Discussing Redis in System Design:

**1. Start with the Problem:**
> "For this system, we need fast data access and [specific requirement]. Redis would be perfect because..."

**2. Choose the Right Pattern:**
> "I'd use [cache-aside/write-through/etc.] pattern because our workload is [read-heavy/write-heavy/mixed]"

**3. Address Scale:**
> "For scaling, we can use Redis Cluster for horizontal scaling or read replicas for read-heavy workloads"

**4. Consider Trade-offs:**
> "The trade-off is [consistency vs performance/memory vs persistence], so I'd configure Redis with [specific settings]"

**5. Monitor and Optimize:**
> "We'd monitor cache hit rates, memory usage, and latency to optimize performance"

This system design approach shows you understand Redis not just as a cache, but as a critical component in modern distributed architectures.