# Valkey Notes

## String

```bash
# Basic String / Number
SET name Alice
SET age 20
GET name
GET age
GET non-existent-key

# Numeric operations
INCR age
DECRBY age 4
GET age
# will return ERR
INCR name 

# String operations
STRLEN name
APPEND name " in wonderland"
GET name
GETRANGE name 0 4
```

## Hashmap

```bash
# Basic
HSET user:1 name Alice age 20
HSET user:2 name Bob age 30
HGET user:1 name
HMGET user:2 name age
# Returns all KEY and VALUE line by line
HGETALL user:1

# HashMap entry as List
# addr: [{"city": "sg", "country": "sg"}, {"city": "jkt", "country": "id"}]
HSET user:1 addr:1:city sg addr:1:country sg addr:2:city jkt addr:2:country id
HGET user:1 addr:1:city
HLEN user:1
```

## List 
- Doubly linked
- May have duplicate records (Duh. Not set)
- Can build FIFO queues with RPUSH and LPOP
- Can build LIFO queues with LPUSH and LPOP

```bash
# Becomes [Carol, Bob, Alice]
LPUSH users Alice Bob Carol
# Get numbers of records
LLEN users
# Peek without affecting the list (can use neg index)
LRANGE users 0 -1
# Pop Carol
LPOP users
# Pop Alice and Bob
RPOP users 2

# Scenario: Cut queue 

# First clear the key
DEL users
# OR non-blocking
UNLINK users

# Becomes [Carol, Bob, Alice]
LPUSH users Alice Bob Carol
LINSERT users BEFORE Bob Camz
LINSERT users AFTER Bob Abed
# Return [Carol, Camz, Bob, Abed, Alice]
LRANGE users 0 -1
# Return Alice
LRANGE users -1 -1
```

## Set
- Unordered
```bash
UNLINK users
SADD users Alice
SADD users Bob
# Len
SCARD users
# Return all
SMEMBERS users
SPOP users
```

## Sorted set
- Sorting order is not determined by the inserting order.
- Sorting order is manually defined by the producer (as a separate field).
- The value in this sorting field is allowed to be updated.

```bash
DEL leaderboard
ZADD leaderboard 50 Alice
ZADD leaderboard 100 Bob
ZADD leaderboard 20 Carol

# ASC (Carol, Alice, Bob)
ZRANGE leaderboard 0 -1
# DESC (Bob, Alice, Carol)
ZREVRANGE leaderboard 0 -1

# Gives more points to Carol
ZINCRBY leaderboard 31 Carol
# Carol overtakes (Bob, Carol, Alice). Return scores too.
ZREVRANGE leaderboard 0 -1 WITHSCORES
```

## Keyspace notification

### Setting expiry

```bash
# Set score=10 for 5s
SET score 10 EX 5
GET score
# With XX it won't take action if the key is not present (i.e. expired)
SET score 20 EX 5 XX
GET score
```

### Subscribing to key-event events

Focus on the EVENT (e.g. `expired` event)
```bash
# Enable key-event notifications (E) and expired events (x), 
# the config is set on the server, not on the client
CONFIG SET notify-keyspace-events Ex
PSUBSCRIBE __keyevent@*__:expired
```

### Subscribing to key-space events

Focus on the KEY. Events will be `set`, `expire`, `del` etc.
```bash
CONFIG SET notify-keyspace-events Kx
PSUBSCRIBE __keyspace@*__:*
```

To reset
```bash
CONFIG SET notify-keyspace-events ""
```

## Advanced 
- Pub/Sub (`SUBSCRIBE notificaitons`, `PUBLISH notificaitons "Hello, world"`)
- Stream (`XADD`, `XRANGE`), Bitmaps, HyperLogLog, Geospatial index
- Pipelining (batching commands)
- Benchmarking (`valkey-benchmark`)
- Full text index and search (`FT.CREATE` / `FT.SEARCH`)
- JSON (`JSON.SET`, `JSON.GET`)
