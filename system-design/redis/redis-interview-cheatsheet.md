# Redis Interview Cheat Sheet - Quick Reference

## Your 30-Second Redis Pitch
> "Redis is an in-memory data structure store that I understand is crucial for modern applications. It's not just a cache - it supports multiple data types like strings, hashes, lists, sets, and sorted sets. I've studied it because most systems I'd work on need fast data access, session management, real-time features, and caching layers that Redis excels at."

## Essential Commands (Memorize These)

### Strings (Basic Key-Value)
```bash
SET key value                    # Set value
GET key                         # Get value
SETEX key seconds value         # Set with expiration
INCR key                        # Increment by 1
INCRBY key increment            # Increment by amount
TTL key                         # Check time to live
```

### Hashes (Objects/Maps)
```bash
HSET key field value            # Set hash field
HGET key field                  # Get hash field
HGETALL key                     # Get all fields
HINCRBY key field increment     # Increment hash field
HDEL key field                  # Delete hash field
```

### Lists (Ordered Collections)
```bash
LPUSH key value                 # Push to left (head)
RPUSH key value                 # Push to right (tail)
LPOP key                        # Pop from left
RPOP key                        # Pop from right
LRANGE key start stop           # Get range of elements
LTRIM key start stop            # Trim list to range
```

### Sets (Unique Collections)
```bash
SADD key member                 # Add to set
SREM key member                 # Remove from set
SISMEMBER key member            # Check membership
SMEMBERS key                    # Get all members
SINTER key1 key2                # Intersection
SUNION key1 key2                # Union
```

### Sorted Sets (Ranked Collections)
```bash
ZADD key score member           # Add with score
ZRANGE key start stop           # Get by rank
ZREVRANGE key start stop        # Get by rank (desc)
ZRANK key member                # Get rank of member
ZSCORE key member               # Get score of member
ZRANGEBYSCORE key min max       # Get by score range
```

## Data Type Decision Matrix

| Use Case | Data Type | Why | Example |
|----------|-----------|-----|---------|
| **Simple cache** | String | Fast, simple | `user:123:name` |
| **User profile** | Hash | Structured data | `user:123 {name, email, age}` |
| **Shopping cart** | Hash | Multiple fields | `cart:123 {item1: qty, item2: qty}` |
| **Recent activity** | List | Ordered, limited size | `user:123:recent_views` |
| **Task queue** | List | FIFO/LIFO operations | `queue:tasks` |
| **Tags/Categories** | Set | Unique items | `post:123:tags` |
| **Followers** | Set | Unique relationships | `user:123:followers` |
| **Leaderboard** | Sorted Set | Ranked by score | `game:leaderboard` |
| **Time series** | Sorted Set | Ordered by timestamp | `metrics:cpu_usage` |

## Common Patterns (Copy-Paste Ready)

### 1. Cache-Aside Pattern
```python
def get_user(user_id):
    # Check cache first
    cached = redis.get(f"user:{user_id}")
    if cached:
        return json.loads(cached)
    
    # Cache miss - get from DB
    user = database.get_user(user_id)
    
    # Store in cache
    redis.setex(f"user:{user_id}", 3600, json.dumps(user))
    return user
```

### 2. Session Management
```python
def create_session(user_id, data):
    session_id = str(uuid.uuid4())
    redis.hset(f"session:{session_id}", mapping=data)
    redis.expire(f"session:{session_id}", 86400)  # 24 hours
    return session_id

def get_session(session_id):
    return redis.hgetall(f"session:{session_id}")
```

### 3. Rate Limiting
```python
def is_rate_limited(user_id, limit=100, window=3600):
    key = f"rate:{user_id}"
    current = int(time.time())
    
    # Remove old entries
    redis.zremrangebyscore(key, 0, current - window)
    
    # Check current count
    if redis.zcard(key) >= limit:
        return True
    
    # Add current request
    redis.zadd(key, {str(current): current})
    redis.expire(key, window)
    return False
```

### 4. Leaderboard
```python
def update_score(player_id, score):
    redis.zadd("leaderboard", {player_id: score})

def get_top_players(count=10):
    return redis.zrevrange("leaderboard", 0, count-1, withscores=True)

def get_player_rank(player_id):
    rank = redis.zrevrank("leaderboard", player_id)
    return rank + 1 if rank is not None else None
```

### 5. Distributed Lock
```python
def acquire_lock(lock_name, timeout=10):
    identifier = str(uuid.uuid4())
    if redis.set(f"lock:{lock_name}", identifier, nx=True, ex=timeout):
        return identifier
    return None

def release_lock(lock_name, identifier):
    lua_script = """
    if redis.call("GET", KEYS[1]) == ARGV[1] then
        return redis.call("DEL", KEYS[1])
    else
        return 0
    end
    """
    return redis.eval(lua_script, 1, f"lock:{lock_name}", identifier)
```

## Architecture Patterns

### Single Instance
```
App → Redis → Database
      ↓
   Fast cache
```
**Use when:** Small to medium scale, simple setup
**Limitations:** Single point of failure, memory limited

### Master-Replica
```
App → Master Redis (writes) → Database
  ↓     ↓
  └→ Replica Redis (reads)
```
**Use when:** Read-heavy workloads
**Benefits:** Read scaling, high availability

### Redis Cluster
```
App → Redis Cluster (auto-sharding) → Database
      ├─ Node 1 (slots 0-5460)
      ├─ Node 2 (slots 5461-10922)
      └─ Node 3 (slots 10923-16383)
```
**Use when:** Large datasets, horizontal scaling needed
**Benefits:** Automatic sharding, high availability

## Performance Optimization

### Memory Optimization
```bash
# Check memory usage
INFO memory

# Optimize data structures
# Instead of: SET user:1:name "John", SET user:1:email "john@example.com"
# Use: HSET user:1 name "John" email "john@example.com"

# Set memory policy
CONFIG SET maxmemory 2gb
CONFIG SET maxmemory-policy allkeys-lru
```

### Connection Pooling
```python
import redis

# Use connection pool
pool = redis.ConnectionPool(host='localhost', port=6379, max_connections=20)
redis_client = redis.Redis(connection_pool=pool)
```

### Pipelining
```python
# Batch multiple commands
pipe = redis.pipeline()
pipe.set("key1", "value1")
pipe.set("key2", "value2")
pipe.get("key1")
results = pipe.execute()
```

## Persistence Options

### RDB (Snapshots)
```bash
# Configuration
save 900 1      # Save if 1+ keys changed in 15 min
save 300 10     # Save if 10+ keys changed in 5 min
save 60 10000   # Save if 10000+ keys changed in 1 min
```
**Pros:** Compact, fast recovery
**Cons:** Potential data loss between snapshots

### AOF (Append Only File)
```bash
# Configuration
appendonly yes
appendfsync everysec  # Sync every second
```
**Pros:** Better durability, minimal data loss
**Cons:** Larger files, slower recovery

**Interview Answer:** "Use both - RDB for fast recovery, AOF for durability"

## Common Interview Questions & Answers

### Q: "What is Redis?"
**Your Answer:**
> "Redis is an in-memory data structure store used as cache, database, and message broker. It supports multiple data types and is extremely fast because data is stored in RAM. It's commonly used for caching, session storage, real-time analytics, and pub/sub messaging."

### Q: "Redis vs Memcached?"
**Your Answer:**
```
Redis:
+ Rich data types (strings, hashes, lists, sets, sorted sets)
+ Persistence options (RDB, AOF)
+ Built-in replication and clustering
+ Pub/Sub messaging
- Higher memory overhead

Memcached:
+ Lower memory overhead
+ Simple key-value operations
+ Multi-threaded
- Only strings
- No persistence
- Limited scaling options

Choose Redis for: Complex data structures, persistence, advanced features
Choose Memcached for: Simple caching, memory efficiency
```

### Q: "How do you handle Redis failover?"
**Your Answer:**
> "For high availability, I'd use Redis Sentinel for automatic failover in master-replica setup, or Redis Cluster for automatic sharding and failover. Sentinel monitors master health and promotes replicas when needed. Cluster provides both sharding and automatic failover."

### Q: "How do you scale Redis?"
**Your Answer:**
```
Vertical Scaling: Increase memory/CPU of single instance
Horizontal Scaling:
- Read replicas for read-heavy workloads
- Redis Cluster for write scaling and large datasets
- Application-level sharding with consistent hashing

Strategy depends on:
- Read vs write ratio
- Data size
- Availability requirements
```

### Q: "Redis data types and use cases?"
**Your Answer:**
```
String: Caching, counters, feature flags
Hash: User profiles, shopping carts, configuration
List: Activity feeds, queues, recent items
Set: Tags, unique visitors, relationships
Sorted Set: Leaderboards, time series, priority queues

Each type is optimized for specific access patterns and operations.
```

## System Design Integration

### E-commerce Platform
```
Components using Redis:
- Product catalog cache (Hash)
- Shopping carts (Hash)
- User sessions (Hash with expiration)
- Inventory counters (String with INCR/DECR)
- Recently viewed items (List)
- Product recommendations (Set operations)
- Flash sale leaderboards (Sorted Set)
```

### Social Media Platform
```
Components using Redis:
- User profiles cache (Hash)
- Activity feeds (List)
- Friend relationships (Set)
- Trending topics (Sorted Set)
- Real-time notifications (Pub/Sub)
- Rate limiting (Sorted Set)
- Session management (Hash)
```

### Gaming Platform
```
Components using Redis:
- Player profiles (Hash)
- Game sessions (Hash with TTL)
- Leaderboards (Sorted Set)
- Matchmaking queues (List)
- Real-time game state (Hash)
- Player statistics (Hash)
- Achievement tracking (Set)
```

## What NOT to Say

❌ **Avoid claiming deep expertise:**
- "I've optimized Redis clusters for millions of users"
- "I've built custom Redis modules"
- "I've tuned Redis for specific hardware configurations"

✅ **Show understanding and learning:**
- "I understand Redis is crucial for modern application performance"
- "I've studied Redis patterns for caching and real-time features"
- "I see Redis as essential for scalable system design"

## Interview Confidence Phrases

### Technical Discussions:
- "Redis is perfect for this use case because of its [specific data type/feature]"
- "I'd use [specific pattern] to ensure [performance/consistency/availability]"
- "The trade-off here is [memory vs persistence/consistency vs performance]"
- "For scaling, we could use [clustering/replication/sharding] depending on the workload"

### Architecture Discussions:
- "Redis would serve as our caching layer to reduce database load"
- "We need Redis for session management across multiple application servers"
- "Real-time features like leaderboards require Redis sorted sets"
- "Rate limiting is best implemented with Redis sliding window pattern"

## 3-Day Learning Priority

### Day 1: Core Concepts
- Understand Redis basics and data types
- Practice basic commands and patterns
- Learn caching strategies

### Day 2: Advanced Patterns
- Session management and rate limiting
- Pub/Sub and messaging patterns
- Distributed locking

### Day 3: System Integration
- Redis in system architecture
- Scaling patterns and trade-offs
- Performance optimization

This cheat sheet gives you everything you need to confidently discuss Redis in interviews while being honest about your learning journey.