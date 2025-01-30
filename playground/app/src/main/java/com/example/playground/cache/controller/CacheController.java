package com.example.playground.cache.controller;

import com.example.playground.cache.service.CacheService;
import com.example.playground.cache.model.CacheRequest;
import com.example.playground.cache.model.CacheResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheService cacheService;

    @Autowired
    public CacheController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping("/{key}")
    public CacheResponse getKey(@PathVariable String key) {
        return cacheService.getStringKey(key);
    }

    @PostMapping("/{key}")
    public void postKey(@PathVariable String key, @RequestBody CacheRequest req) {
        cacheService.putStringKey(key, req);
    }

    @DeleteMapping("/{key}")
    public void deleteKey(@PathVariable String key) {
        cacheService.deleteKey(key);
    }

}
