# Redis Interview Essentials - Quick Learning Guide

## Your Redis Story (For Interviews)
> "While I haven't worked extensively with Redis in production, I understand it's a critical component for caching, session management, and real-time applications. I've been studying it because most modern architectures require fast data access patterns that Redis excels at."

## What Redis Actually Is (Simple Explanation)

**Redis = Remote Dictionary Server**
- **In-memory database**: Data stored in RAM (super fast)
- **Key-value store**: Like a giant HashMap but distributed
- **Data structures**: Not just strings, but lists, sets, hashes, etc.
- **Persistence**: Can save to disk for durability
- **Single-threaded**: No complex locking, but uses event loop

## Core Redis Data Types (Memorize These)

### 1. Strings (Most Basic)
```bash
# Set and get
SET user:1001:name "John Doe"
GET user:1001:name
# Returns: "John Doe"

# Increment (atomic operations)
SET page:views 100
INCR page:views
# Returns: 101

# Expiration
SET session:abc123 "user_data" EX 3600  # Expires in 1 hour
TTL session:abc123  # Check remaining time
```

**Interview Use Cases:**
- Session storage
- Counters (page views, likes)
- Caching API responses
- Feature flags

### 2. Hashes (Like Objects/Maps)
```bash
# User profile storage
HSET user:1001 name "John" email "john@example.com" age 30
HGET user:1001 name
# Returns: "John"

HGETALL user:1001
# Returns: name="John", email="john@example.com", age=30

# Increment hash field
HINCRBY user:1001 login_count 1
```

**Interview Use Cases:**
- User profiles
- Product catalogs
- Configuration settings
- Shopping carts

### 3. Lists (Ordered Collections)
```bash
# Queue operations (FIFO)
LPUSH queue:tasks "task1" "task2" "task3"
RPOP queue:tasks
# Returns: "task1"

# Stack operations (LIFO)
LPUSH stack:items "item1" "item2"
LPOP stack:items
# Returns: "item2"

# Recent items (timeline)
LPUSH user:1001:recent_views "product123"
LTRIM user:1001:recent_views 0 9  # Keep only last 10 items
```

**Interview Use Cases:**
- Task queues
- Activity feeds
- Recent items lists
- Message queues

### 4. Sets (Unique Collections)
```bash
# Add unique items
SADD user:1001:interests "technology" "sports" "music"
SADD user:1002:interests "technology" "art" "music"

# Check membership
SISMEMBER user:1001:interests "technology"
# Returns: 1 (true)

# Set operations
SINTER user:1001:interests user:1002:interests
# Returns: "technology", "music" (common interests)

SUNION user:1001:interests user:1002:interests
# Returns: all unique interests from both users
```

**Interview Use Cases:**
- Tags and categories
- Unique visitors tracking
- Friend relationships
- Recommendation systems

### 5. Sorted Sets (Ranked Collections)
```bash
# Leaderboard
ZADD leaderboard 1500 "player1" 1200 "player2" 1800 "player3"

# Get top players
ZREVRANGE leaderboard 0 2 WITHSCORES
# Returns: player3(1800), player1(1500), player2(1200)

# Get player rank
ZREVRANK leaderboard "player1"
# Returns: 1 (second place, 0-indexed)

# Range by score
ZRANGEBYSCORE leaderboard 1000 1600
# Returns: players with scores between 1000-1600
```

**Interview Use Cases:**
- Leaderboards
- Priority queues
- Time-series data
- Rate limiting

## Essential Redis Patterns (Interview Favorites)

### 1. Caching Pattern
```python
import redis
import json

r = redis.Redis(host='localhost', port=6379, db=0)

def get_user_profile(user_id):
    # Try cache first
    cache_key = f"user:profile:{user_id}"
    cached_data = r.get(cache_key)
    
    if cached_data:
        return json.loads(cached_data)
    
    # Cache miss - get from database
    user_data = database.get_user(user_id)  # Expensive DB call
    
    # Store in cache for 1 hour
    r.setex(cache_key, 3600, json.dumps(user_data))
    
    return user_data
```

**Interview Answer:**
> "This is the most common Redis pattern. Check cache first, if miss then fetch from database and cache the result. Reduces database load and improves response times."

### 2. Session Management
```python
def create_session(user_id, session_data):
    session_id = generate_session_id()
    session_key = f"session:{session_id}"
    
    # Store session data with 24-hour expiration
    r.hset(session_key, mapping=session_data)
    r.expire(session_key, 86400)  # 24 hours
    
    return session_id

def get_session(session_id):
    session_key = f"session:{session_id}"
    return r.hgetall(session_key)

def extend_session(session_id):
    session_key = f"session:{session_id}"
    r.expire(session_key, 86400)  # Reset to 24 hours
```

**Interview Answer:**
> "Redis is perfect for sessions because it's fast, supports expiration, and can be shared across multiple application servers. Much better than storing sessions in application memory."

### 3. Rate Limiting (Sliding Window)
```python
def is_rate_limited(user_id, limit=100, window=3600):
    """Allow 100 requests per hour per user"""
    key = f"rate_limit:{user_id}"
    current_time = int(time.time())
    
    # Remove old entries outside the window
    r.zremrangebyscore(key, 0, current_time - window)
    
    # Count current requests
    current_count = r.zcard(key)
    
    if current_count >= limit:
        return True  # Rate limited
    
    # Add current request
    r.zadd(key, {str(current_time): current_time})
    r.expire(key, window)
    
    return False  # Not rate limited
```

**Interview Answer:**
> "Using sorted sets with timestamps, we can implement sliding window rate limiting. Remove old entries, count current requests, and add new request if under limit."

### 4. Distributed Locking
```python
def acquire_lock(lock_name, timeout=10):
    """Simple distributed lock"""
    lock_key = f"lock:{lock_name}"
    identifier = str(uuid.uuid4())
    
    # Try to acquire lock with expiration
    if r.set(lock_key, identifier, nx=True, ex=timeout):
        return identifier  # Lock acquired
    
    return None  # Lock not acquired

def release_lock(lock_name, identifier):
    """Release lock only if we own it"""
    lock_key = f"lock:{lock_name}"
    
    # Lua script for atomic check-and-delete
    lua_script = """
    if redis.call("GET", KEYS[1]) == ARGV[1] then
        return redis.call("DEL", KEYS[1])
    else
        return 0
    end
    """
    
    return r.eval(lua_script, 1, lock_key, identifier)
```

**Interview Answer:**
> "Distributed locks prevent race conditions in distributed systems. Use SET with NX (not exists) and EX (expiration) for atomic lock acquisition."

## Redis Architecture & Deployment

### Single Instance vs Cluster

#### Single Redis Instance
```
┌─────────────────┐
│   Application   │
└─────────────────┘
         │
         ▼
┌─────────────────┐
│  Redis Server   │
│  (Port 6379)    │
│                 │
│ ┌─────────────┐ │
│ │   Memory    │ │ ← All data here
│ │   (RAM)     │ │
│ └─────────────┘ │
│                 │
│ ┌─────────────┐ │
│ │    Disk     │ │ ← Persistence (optional)
│ │ (RDB/AOF)   │ │
│ └─────────────┘ │
└─────────────────┘
```

**Pros:** Simple, fast, consistent
**Cons:** Single point of failure, limited by single machine memory

#### Redis Cluster
```
┌─────────────────┐
│   Application   │
└─────────────────┘
         │
         ▼
┌─────────────────────────────────────────┐
│            Redis Cluster                │
│                                         │
│ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ │
│ │   Node 1    │ │   Node 2    │ │   Node 3    │ │
│ │ Slots:      │ │ Slots:      │ │ Slots:      │ │
│ │ 0-5460      │ │ 5461-10922  │ │ 10923-16383 │ │
│ └─────────────┘ └─────────────┘ └─────────────┘ │
│                                         │
│ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐ │
│ │  Replica 1  │ │  Replica 2  │ │  Replica 3  │ │
│ │ (Node 1)    │ │ (Node 2)    │ │ (Node 3)    │ │
│ └─────────────┘ └─────────────┘ └─────────────┘ │
└─────────────────────────────────────────┘
```

**Pros:** High availability, horizontal scaling, automatic failover
**Cons:** More complex, some commands don't work across slots

### Redis Persistence Options

#### RDB (Redis Database Backup)
```bash
# Configuration
save 900 1      # Save if at least 1 key changed in 900 seconds
save 300 10     # Save if at least 10 keys changed in 300 seconds
save 60 10000   # Save if at least 10000 keys changed in 60 seconds
```

**Pros:** Compact, fast recovery, good for backups
**Cons:** Data loss possible between snapshots

#### AOF (Append Only File)
```bash
# Configuration
appendonly yes
appendfsync everysec  # Sync to disk every second
```

**Pros:** Better durability, minimal data loss
**Cons:** Larger files, slower recovery

**Interview Answer:**
> "RDB is good for backups and faster restarts. AOF is better for durability. In production, use both - RDB for fast recovery and AOF for minimal data loss."

## Common Interview Scenarios

### Scenario 1: Design a Caching Layer
```
Question: "Design a caching system for an e-commerce product catalog"

Your Architecture:
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Client    │───▶│ Application │───▶│  Database   │
└─────────────┘    │   Server    │    │ (MySQL)     │
                   └─────────────┘    └─────────────┘
                          │                   ▲
                          ▼                   │
                   ┌─────────────┐           │
                   │    Redis    │───────────┘
                   │   Cache     │
                   └─────────────┘

Cache Strategy:
1. Check Redis for product data
2. If cache miss, query database
3. Store result in Redis with TTL
4. Return data to client

Key Patterns:
- product:123 → Product details
- category:electronics → Product list
- user:456:cart → Shopping cart
```

### Scenario 2: Real-time Leaderboard
```
Question: "Design a real-time gaming leaderboard"

Your Solution:
# Store player scores
ZADD game:leaderboard 1500 "player1" 2000 "player2" 1800 "player3"

# Get top 10 players
ZREVRANGE game:leaderboard 0 9 WITHSCORES

# Get player's rank
ZREVRANK game:leaderboard "player1"

# Update score (atomic)
ZINCRBY game:leaderboard 100 "player1"

# Get players around a specific player
ZREVRANGE game:leaderboard (rank-2) (rank+2) WITHSCORES
```

### Scenario 3: Session Store for Microservices
```
Question: "How would you handle user sessions across multiple microservices?"

Your Architecture:
┌─────────────┐    ┌─────────────┐
│   User      │───▶│ Load        │
│   Request   │    │ Balancer    │
└─────────────┘    └─────────────┘
                          │
        ┌─────────────────┼─────────────────┐
        │                 │                 │
        ▼                 ▼                 ▼
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│ Service A   │    │ Service B   │    │ Service C   │
└─────────────┘    └─────────────┘    └─────────────┘
        │                 │                 │
        └─────────────────┼─────────────────┘
                          │
                          ▼
                   ┌─────────────┐
                   │    Redis    │
                   │  (Sessions) │
                   └─────────────┘

Session Data:
HSET session:abc123 user_id 456 role "admin" last_activity 1634567890
EXPIRE session:abc123 3600
```

## Performance & Optimization

### Memory Optimization
```bash
# Check memory usage
INFO memory

# Optimize data types
# Instead of: SET user:1001:name "John"
# Use hash:   HSET users 1001:name "John"

# Use appropriate data types
# For small lists: Use hashes instead of separate keys
# For large datasets: Consider Redis Cluster

# Set memory policies
maxmemory 2gb
maxmemory-policy allkeys-lru  # Evict least recently used keys
```

### Connection Pooling
```python
import redis.connection

# Connection pool (reuse connections)
pool = redis.ConnectionPool(
    host='localhost',
    port=6379,
    max_connections=20,
    retry_on_timeout=True
)

r = redis.Redis(connection_pool=pool)
```

## Redis vs Other Technologies

### Redis vs Memcached
| Feature | Redis | Memcached | When to Use |
|---------|-------|-----------|-------------|
| **Data Types** | Rich (strings, lists, sets, etc.) | Only strings | Redis for complex data, Memcached for simple cache |
| **Persistence** | Yes (RDB/AOF) | No | Redis if you need durability |
| **Clustering** | Built-in | Third-party | Redis for easier scaling |
| **Memory Usage** | Higher overhead | Lower overhead | Memcached for pure caching |

### Redis vs Database
| Use Case | Redis | Traditional DB | Decision |
|----------|-------|----------------|----------|
| **Caching** | Perfect | Overkill | Always Redis |
| **Session Storage** | Excellent | Possible | Redis (faster, TTL support) |
| **Complex Queries** | Limited | Excellent | Database |
| **ACID Transactions** | Limited | Full support | Database for critical data |
| **Large Datasets** | Memory limited | Disk-based | Database for big data |

## Quick Interview Answers

### Q: "What is Redis and when would you use it?"
**Your Answer:**
> "Redis is an in-memory data structure store used as cache, database, and message broker. I'd use it for caching frequently accessed data, session management, real-time leaderboards, and rate limiting. It's much faster than traditional databases because data is stored in RAM."

### Q: "How does Redis handle persistence?"
**Your Answer:**
> "Redis offers two persistence options: RDB creates point-in-time snapshots, which is fast and compact. AOF logs every write operation, providing better durability. In production, I'd use both - RDB for fast recovery and AOF to minimize data loss."

### Q: "How would you scale Redis?"
**Your Answer:**
> "For read scaling, use Redis replicas. For write scaling and larger datasets, use Redis Cluster which automatically shards data across multiple nodes. You can also partition data at the application level using consistent hashing."

### Q: "What are Redis data types and their use cases?"
**Your Answer:**
> "Strings for caching and counters, Hashes for objects like user profiles, Lists for queues and timelines, Sets for unique collections and tags, Sorted Sets for leaderboards and rankings. Each type is optimized for specific access patterns."

## Learning Priority (3-Day Plan)

### Day 1: Core Concepts
- Understand Redis basics and data types
- Practice basic commands (SET, GET, HSET, LPUSH, etc.)
- Learn caching patterns

### Day 2: Advanced Patterns
- Session management
- Rate limiting
- Distributed locking
- Pub/Sub basics

### Day 3: Architecture & Scaling
- Persistence options (RDB vs AOF)
- Clustering and replication
- Performance optimization
- Integration patterns

## What NOT to Claim
❌ "I've built large-scale Redis clusters in production"
❌ "I've optimized Redis for specific hardware configurations"
❌ "I've implemented custom Redis modules"

## What TO Say
✅ "I understand Redis is crucial for modern application performance"
✅ "I've studied Redis patterns for caching and session management"
✅ "I see Redis as essential for real-time features and scaling"

This guide gives you solid Redis knowledge for interviews while being honest about your experience level. Focus on understanding the concepts and use cases rather than claiming deep operational experience.