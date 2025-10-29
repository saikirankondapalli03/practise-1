# Redis Hands-On Examples - Interview Practice

## Quick Setup for Demo (If Asked to Code)

### Basic Connection Setup
```python
import redis
import json
import time
import uuid

# Connect to Redis
r = redis.Redis(host='localhost', port=6379, db=0, decode_responses=True)

# Test connection
try:
    r.ping()
    print("Connected to Redis!")
except redis.ConnectionError:
    print("Could not connect to Redis")
```

## Interview-Ready Code Examples

### 1. Caching Layer Implementation
```python
class CacheManager:
    def __init__(self, redis_client, default_ttl=3600):
        self.redis = redis_client
        self.default_ttl = default_ttl
    
    def get_or_set(self, key, fetch_function, ttl=None):
        """Get from cache or fetch and cache"""
        # Try cache first
        cached_value = self.redis.get(key)
        if cached_value:
            return json.loads(cached_value)
        
        # Cache miss - fetch data
        data = fetch_function()
        
        # Cache the result
        ttl = ttl or self.default_ttl
        self.redis.setex(key, ttl, json.dumps(data))
        
        return data
    
    def invalidate(self, pattern):
        """Invalidate cache keys matching pattern"""
        keys = self.redis.keys(pattern)
        if keys:
            self.redis.delete(*keys)

# Usage example
cache = CacheManager(r)

def get_user_profile(user_id):
    def fetch_from_db():
        # Simulate expensive database call
        return {
            "id": user_id,
            "name": "John Doe",
            "email": "john@example.com",
            "preferences": {"theme": "dark", "notifications": True}
        }
    
    return cache.get_or_set(f"user:profile:{user_id}", fetch_from_db, ttl=1800)

# Interview demo
profile = get_user_profile(123)  # First call: fetches from DB
profile = get_user_profile(123)  # Second call: returns from cache
```

### 2. Session Management System
```python
class SessionManager:
    def __init__(self, redis_client, session_ttl=86400):
        self.redis = redis_client
        self.session_ttl = session_ttl
    
    def create_session(self, user_id, user_data):
        """Create new session"""
        session_id = str(uuid.uuid4())
        session_key = f"session:{session_id}"
        
        session_data = {
            "user_id": str(user_id),
            "created_at": str(int(time.time())),
            "last_activity": str(int(time.time())),
            **user_data
        }
        
        # Store session with expiration
        self.redis.hset(session_key, mapping=session_data)
        self.redis.expire(session_key, self.session_ttl)
        
        return session_id
    
    def get_session(self, session_id):
        """Get session data"""
        session_key = f"session:{session_id}"
        session_data = self.redis.hgetall(session_key)
        
        if not session_data:
            return None
        
        # Update last activity
        self.redis.hset(session_key, "last_activity", str(int(time.time())))
        self.redis.expire(session_key, self.session_ttl)
        
        return session_data
    
    def destroy_session(self, session_id):
        """Delete session"""
        session_key = f"session:{session_id}"
        return self.redis.delete(session_key)
    
    def get_active_sessions(self, user_id):
        """Get all active sessions for a user"""
        pattern = "session:*"
        sessions = []
        
        for key in self.redis.scan_iter(match=pattern):
            session_data = self.redis.hgetall(key)
            if session_data.get("user_id") == str(user_id):
                sessions.append({
                    "session_id": key.split(":")[1],
                    "last_activity": session_data.get("last_activity"),
                    "created_at": session_data.get("created_at")
                })
        
        return sessions

# Usage example
session_mgr = SessionManager(r)

# Create session
session_id = session_mgr.create_session(
    user_id=123,
    user_data={"role": "admin", "permissions": ["read", "write"]}
)

# Get session
session_data = session_mgr.get_session(session_id)
print(f"User ID: {session_data['user_id']}")
```

### 3. Rate Limiting Implementation
```python
class RateLimiter:
    def __init__(self, redis_client):
        self.redis = redis_client
    
    def is_allowed(self, identifier, limit, window_seconds):
        """Sliding window rate limiter"""
        key = f"rate_limit:{identifier}"
        current_time = time.time()
        
        # Remove old entries
        self.redis.zremrangebyscore(key, 0, current_time - window_seconds)
        
        # Count current requests
        current_count = self.redis.zcard(key)
        
        if current_count >= limit:
            return False, 0  # Rate limited
        
        # Add current request
        self.redis.zadd(key, {str(current_time): current_time})
        self.redis.expire(key, window_seconds)
        
        remaining = limit - current_count - 1
        return True, remaining
    
    def get_reset_time(self, identifier, window_seconds):
        """Get when the rate limit resets"""
        key = f"rate_limit:{identifier}"
        oldest_request = self.redis.zrange(key, 0, 0, withscores=True)
        
        if not oldest_request:
            return 0
        
        oldest_time = oldest_request[0][1]
        reset_time = oldest_time + window_seconds
        return max(0, reset_time - time.time())

# Usage example
rate_limiter = RateLimiter(r)

def api_endpoint(user_id):
    # Allow 100 requests per hour per user
    allowed, remaining = rate_limiter.is_allowed(
        identifier=f"user:{user_id}",
        limit=100,
        window_seconds=3600
    )
    
    if not allowed:
        reset_time = rate_limiter.get_reset_time(f"user:{user_id}", 3600)
        return {
            "error": "Rate limit exceeded",
            "reset_in_seconds": reset_time
        }
    
    return {
        "data": "API response",
        "rate_limit_remaining": remaining
    }

# Test rate limiting
for i in range(5):
    result = api_endpoint(123)
    print(f"Request {i+1}: {result}")
```

### 4. Real-time Leaderboard
```python
class Leaderboard:
    def __init__(self, redis_client, leaderboard_name):
        self.redis = redis_client
        self.key = f"leaderboard:{leaderboard_name}"
    
    def add_score(self, player_id, score):
        """Add or update player score"""
        return self.redis.zadd(self.key, {player_id: score})
    
    def increment_score(self, player_id, increment):
        """Increment player score"""
        return self.redis.zincrby(self.key, increment, player_id)
    
    def get_top_players(self, count=10):
        """Get top N players"""
        players = self.redis.zrevrange(self.key, 0, count-1, withscores=True)
        return [
            {"player_id": player, "score": int(score), "rank": i+1}
            for i, (player, score) in enumerate(players)
        ]
    
    def get_player_rank(self, player_id):
        """Get player's rank (1-based)"""
        rank = self.redis.zrevrank(self.key, player_id)
        return rank + 1 if rank is not None else None
    
    def get_player_score(self, player_id):
        """Get player's current score"""
        score = self.redis.zscore(self.key, player_id)
        return int(score) if score is not None else None
    
    def get_players_around(self, player_id, range_size=2):
        """Get players around a specific player"""
        rank = self.redis.zrevrank(self.key, player_id)
        if rank is None:
            return []
        
        start = max(0, rank - range_size)
        end = rank + range_size
        
        players = self.redis.zrevrange(self.key, start, end, withscores=True)
        return [
            {
                "player_id": player,
                "score": int(score),
                "rank": start + i + 1
            }
            for i, (player, score) in enumerate(players)
        ]

# Usage example
leaderboard = Leaderboard(r, "game_scores")

# Add some scores
leaderboard.add_score("player1", 1500)
leaderboard.add_score("player2", 2000)
leaderboard.add_score("player3", 1800)
leaderboard.increment_score("player1", 200)  # Now 1700

# Get rankings
top_players = leaderboard.get_top_players(3)
print("Top 3 players:", top_players)

player_rank = leaderboard.get_player_rank("player1")
print(f"Player1 rank: {player_rank}")
```

### 5. Distributed Lock Implementation
```python
class DistributedLock:
    def __init__(self, redis_client, lock_name, timeout=10):
        self.redis = redis_client
        self.lock_name = lock_name
        self.timeout = timeout
        self.identifier = None
    
    def acquire(self):
        """Acquire the lock"""
        self.identifier = str(uuid.uuid4())
        lock_key = f"lock:{self.lock_name}"
        
        # Try to acquire lock
        acquired = self.redis.set(
            lock_key,
            self.identifier,
            nx=True,  # Only set if key doesn't exist
            ex=self.timeout  # Set expiration
        )
        
        return acquired is not None
    
    def release(self):
        """Release the lock (only if we own it)"""
        if not self.identifier:
            return False
        
        lock_key = f"lock:{self.lock_name}"
        
        # Lua script for atomic check-and-delete
        lua_script = """
        if redis.call("GET", KEYS[1]) == ARGV[1] then
            return redis.call("DEL", KEYS[1])
        else
            return 0
        end
        """
        
        result = self.redis.eval(lua_script, 1, lock_key, self.identifier)
        self.identifier = None
        return result == 1
    
    def __enter__(self):
        """Context manager entry"""
        if not self.acquire():
            raise Exception(f"Could not acquire lock: {self.lock_name}")
        return self
    
    def __exit__(self, exc_type, exc_val, exc_tb):
        """Context manager exit"""
        self.release()

# Usage example
def critical_section():
    """Function that needs exclusive access"""
    with DistributedLock(r, "critical_resource", timeout=30):
        print("Performing critical operation...")
        time.sleep(2)  # Simulate work
        print("Critical operation completed")

# Test concurrent access
import threading

def worker(worker_id):
    try:
        print(f"Worker {worker_id} trying to acquire lock...")
        critical_section()
        print(f"Worker {worker_id} completed")
    except Exception as e:
        print(f"Worker {worker_id} failed: {e}")

# Start multiple workers
threads = []
for i in range(3):
    t = threading.Thread(target=worker, args=(i,))
    threads.append(t)
    t.start()

for t in threads:
    t.join()
```

### 6. Pub/Sub Messaging System
```python
class MessageBroker:
    def __init__(self, redis_client):
        self.redis = redis_client
        self.pubsub = self.redis.pubsub()
    
    def publish(self, channel, message):
        """Publish message to channel"""
        message_data = {
            "timestamp": time.time(),
            "data": message
        }
        return self.redis.publish(channel, json.dumps(message_data))
    
    def subscribe(self, channels, callback):
        """Subscribe to channels and process messages"""
        if isinstance(channels, str):
            channels = [channels]
        
        self.pubsub.subscribe(*channels)
        
        try:
            for message in self.pubsub.listen():
                if message['type'] == 'message':
                    try:
                        data = json.loads(message['data'])
                        callback(message['channel'], data)
                    except json.JSONDecodeError:
                        print(f"Invalid JSON in message: {message['data']}")
        except KeyboardInterrupt:
            print("Stopping subscriber...")
        finally:
            self.pubsub.close()

# Usage example
broker = MessageBroker(r)

# Publisher
def send_notifications():
    broker.publish("user_notifications", {
        "user_id": 123,
        "type": "order_shipped",
        "message": "Your order has been shipped!"
    })
    
    broker.publish("system_alerts", {
        "level": "warning",
        "message": "High CPU usage detected"
    })

# Subscriber
def handle_message(channel, data):
    print(f"Received on {channel}: {data}")
    
    if channel == "user_notifications":
        # Send email, push notification, etc.
        print(f"Sending notification to user {data['data']['user_id']}")
    elif channel == "system_alerts":
        # Log alert, send to monitoring system
        print(f"System alert: {data['data']['message']}")

# In a real application, subscriber would run in separate process
# broker.subscribe(["user_notifications", "system_alerts"], handle_message)
```

### 7. Shopping Cart Implementation
```python
class ShoppingCart:
    def __init__(self, redis_client):
        self.redis = redis_client
    
    def add_item(self, user_id, product_id, quantity=1, price=None):
        """Add item to cart"""
        cart_key = f"cart:{user_id}"
        item_key = f"item:{product_id}"
        
        # Store item details
        item_data = {
            "product_id": product_id,
            "quantity": str(quantity),
            "added_at": str(int(time.time()))
        }
        
        if price:
            item_data["price"] = str(price)
        
        self.redis.hset(cart_key, item_key, json.dumps(item_data))
        
        # Set cart expiration (30 days)
        self.redis.expire(cart_key, 30 * 24 * 3600)
    
    def remove_item(self, user_id, product_id):
        """Remove item from cart"""
        cart_key = f"cart:{user_id}"
        item_key = f"item:{product_id}"
        return self.redis.hdel(cart_key, item_key)
    
    def update_quantity(self, user_id, product_id, quantity):
        """Update item quantity"""
        cart_key = f"cart:{user_id}"
        item_key = f"item:{product_id}"
        
        item_data = self.redis.hget(cart_key, item_key)
        if not item_data:
            return False
        
        item = json.loads(item_data)
        item["quantity"] = str(quantity)
        
        self.redis.hset(cart_key, item_key, json.dumps(item))
        return True
    
    def get_cart(self, user_id):
        """Get all cart items"""
        cart_key = f"cart:{user_id}"
        cart_data = self.redis.hgetall(cart_key)
        
        items = []
        total_items = 0
        
        for item_key, item_data in cart_data.items():
            if item_key.startswith("item:"):
                item = json.loads(item_data)
                items.append(item)
                total_items += int(item["quantity"])
        
        return {
            "items": items,
            "total_items": total_items,
            "cart_age_seconds": self.redis.ttl(cart_key)
        }
    
    def clear_cart(self, user_id):
        """Clear entire cart"""
        cart_key = f"cart:{user_id}"
        return self.redis.delete(cart_key)

# Usage example
cart = ShoppingCart(r)

# Add items to cart
cart.add_item(user_id=123, product_id="laptop_001", quantity=1, price=999.99)
cart.add_item(user_id=123, product_id="mouse_002", quantity=2, price=29.99)

# Get cart contents
cart_contents = cart.get_cart(123)
print("Cart contents:", cart_contents)

# Update quantity
cart.update_quantity(123, "mouse_002", 3)

# Remove item
cart.remove_item(123, "laptop_001")
```

## Performance Testing Examples

### 1. Benchmark Redis Operations
```python
import time

def benchmark_redis_operations():
    """Benchmark common Redis operations"""
    
    # Test SET operations
    start_time = time.time()
    for i in range(10000):
        r.set(f"test_key_{i}", f"value_{i}")
    set_time = time.time() - start_time
    
    # Test GET operations
    start_time = time.time()
    for i in range(10000):
        r.get(f"test_key_{i}")
    get_time = time.time() - start_time
    
    # Test HSET operations
    start_time = time.time()
    for i in range(10000):
        r.hset(f"hash_{i}", "field1", f"value_{i}")
    hset_time = time.time() - start_time
    
    # Cleanup
    r.flushdb()
    
    print(f"SET operations (10k): {set_time:.2f}s ({10000/set_time:.0f} ops/sec)")
    print(f"GET operations (10k): {get_time:.2f}s ({10000/get_time:.0f} ops/sec)")
    print(f"HSET operations (10k): {hset_time:.2f}s ({10000/hset_time:.0f} ops/sec)")

# Run benchmark
benchmark_redis_operations()
```

### 2. Memory Usage Analysis
```python
def analyze_memory_usage():
    """Analyze Redis memory usage patterns"""
    
    # Get initial memory info
    initial_info = r.info('memory')
    initial_memory = initial_info['used_memory']
    
    print(f"Initial memory usage: {initial_memory / 1024 / 1024:.2f} MB")
    
    # Add data and measure
    for i in range(1000):
        # Different data types
        r.set(f"string:{i}", "x" * 100)  # 100-char strings
        r.hset(f"hash:{i}", mapping={f"field_{j}": f"value_{j}" for j in range(10)})
        r.lpush(f"list:{i}", *[f"item_{j}" for j in range(10)])
    
    # Get final memory info
    final_info = r.info('memory')
    final_memory = final_info['used_memory']
    
    print(f"Final memory usage: {final_memory / 1024 / 1024:.2f} MB")
    print(f"Memory increase: {(final_memory - initial_memory) / 1024 / 1024:.2f} MB")
    print(f"Keys in database: {r.dbsize()}")
    
    # Cleanup
    r.flushdb()

analyze_memory_usage()
```

## Interview Scenarios Practice

### Scenario 1: Design a URL Shortener Cache
```python
class URLShortener:
    def __init__(self, redis_client):
        self.redis = redis_client
    
    def shorten_url(self, long_url, custom_short=None):
        """Create short URL"""
        if custom_short:
            short_code = custom_short
        else:
            # Generate short code
            short_code = self.generate_short_code()
        
        # Check if already exists
        if self.redis.exists(f"short:{short_code}"):
            return None  # Already exists
        
        # Store mapping
        self.redis.setex(f"short:{short_code}", 86400 * 30, long_url)  # 30 days
        
        # Store reverse mapping for analytics
        self.redis.setex(f"long:{hash(long_url)}", 86400 * 30, short_code)
        
        # Initialize click counter
        self.redis.set(f"clicks:{short_code}", 0)
        
        return short_code
    
    def expand_url(self, short_code):
        """Get original URL and increment counter"""
        long_url = self.redis.get(f"short:{short_code}")
        if long_url:
            # Increment click counter
            self.redis.incr(f"clicks:{short_code}")
        return long_url
    
    def get_stats(self, short_code):
        """Get URL statistics"""
        clicks = self.redis.get(f"clicks:{short_code}")
        return {"clicks": int(clicks) if clicks else 0}
    
    def generate_short_code(self):
        """Generate unique short code"""
        import random
        import string
        return ''.join(random.choices(string.ascii_letters + string.digits, k=6))
```

### Scenario 2: Real-time Chat System
```python
class ChatSystem:
    def __init__(self, redis_client):
        self.redis = redis_client
    
    def send_message(self, room_id, user_id, message):
        """Send message to chat room"""
        message_data = {
            "user_id": user_id,
            "message": message,
            "timestamp": time.time()
        }
        
        # Add to room message list (keep last 100 messages)
        room_key = f"chat:room:{room_id}"
        self.redis.lpush(room_key, json.dumps(message_data))
        self.redis.ltrim(room_key, 0, 99)  # Keep only last 100
        
        # Publish to subscribers
        self.redis.publish(f"chat:room:{room_id}", json.dumps(message_data))
        
        # Update user's last activity
        self.redis.hset(f"chat:user:{user_id}", "last_seen", int(time.time()))
    
    def get_recent_messages(self, room_id, count=50):
        """Get recent messages from room"""
        room_key = f"chat:room:{room_id}"
        messages = self.redis.lrange(room_key, 0, count-1)
        return [json.loads(msg) for msg in messages]
    
    def join_room(self, room_id, user_id):
        """Add user to room"""
        self.redis.sadd(f"chat:room:{room_id}:users", user_id)
        self.redis.hset(f"chat:user:{user_id}", "current_room", room_id)
    
    def leave_room(self, room_id, user_id):
        """Remove user from room"""
        self.redis.srem(f"chat:room:{room_id}:users", user_id)
        self.redis.hdel(f"chat:user:{user_id}", "current_room")
```

This hands-on guide provides practical Redis implementations that you can discuss confidently in interviews. Each example demonstrates real-world patterns and shows your understanding of Redis capabilities.