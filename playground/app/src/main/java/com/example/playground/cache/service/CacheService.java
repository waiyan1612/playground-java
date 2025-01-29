package com.example.playground.cache.service;

import com.example.playground.cache.model.CacheRequest;
import com.example.playground.cache.model.CacheResponse;
import com.example.playground.base.exception.NotFoundException;
import io.valkey.JedisPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public CacheResponse getStringKey(String key) {
        String value = pool.getResource().get(key);
        if (value == null) {
            throw new NotFoundException("Key not found in cache: " + key);
        }
        long expires = pool.getResource().expireTime(key);
        Optional<Long> optExpires = expires < 0 ? Optional.empty() : Optional.of(expires);
        return new CacheResponse(key, value, optExpires);
    }

    public void putStringKey(String key, CacheRequest req) {
        if (req.ttl().isEmpty()) {
            pool.getResource().set(key, req.value());
        } else {
            pool.getResource().setex(key, req.ttl().get(), req.value());
        }
    }

    public void deleteKey(String key) {
        pool.getResource().del(key);
    }
}
