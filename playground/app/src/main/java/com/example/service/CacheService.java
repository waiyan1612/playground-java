package com.example.service;

import io.valkey.JedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Check <a href="https://www.baeldung.com/jedis-java-redis-client-library#Structures">Baeldung tutorial</a>
 * For other structs such as lists, sets, maps and sorted sets.
 */
@Service
public class CacheService {

    private final JedisPool pool;

    @Autowired
    public CacheService(JedisPool pool) {
        this.pool = pool;
    }

    public String getStringKey(String key) {
        return pool.getResource().get(key);
    }

    public void putStringKey(String key, String value, Integer secondsToExpire) {
        if (secondsToExpire == null) {
            pool.getResource().set(key, value);
        } else {
            pool.getResource().setex(key, secondsToExpire, value);
        }
    }

    public void deleteKey(String key) {
        pool.getResource().del(key);
    }
}
