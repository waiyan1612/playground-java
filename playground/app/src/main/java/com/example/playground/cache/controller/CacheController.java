package com.example.playground.cache.controller;

import com.example.playground.cache.service.CacheService;
import com.example.playground.cache.model.CacheRequest;
import com.example.playground.cache.model.CacheResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private static final Logger log = LoggerFactory.getLogger(CacheController.class);

    private final CacheService cacheService;

    @Autowired
    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping("/{key}")
    public CacheResponse getKey(@PathVariable String key) {
        log.info("Received request to get {} from the cache", key);
        return cacheService.getStringKey(key);
    }

    @PostMapping("/{key}")
    public void postKey(@PathVariable String key, @RequestBody CacheRequest req) {
        log.info("Received request to post {} to the cache", req);
        cacheService.putStringKey(key, req);
    }

    @DeleteMapping("/{key}")
    public void deleteKey(@PathVariable String key) {
        log.info("Received request to delete {} from the cache", key);
        cacheService.deleteKey(key);
    }

}
