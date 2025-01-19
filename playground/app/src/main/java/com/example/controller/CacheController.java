package com.example.controller;

import com.example.response.CacheResponse;
import com.example.service.CacheService;
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
    public CacheResponse getKey(@PathVariable(value = "key") String key) {
        log.info("Received request to get {} from the cache", key);
        String value = cacheService.getStringKey(key);
        log.info("Found {}:{} from the cache", key, value);
        return new CacheResponse(key, value);
    }

    @PostMapping("/{key}/{value}")
    public CacheResponse postKey(@PathVariable(value = "key") String key,
                                 @PathVariable(value = "value") String value,
                                 @RequestParam(value = "secondsToExpire") Integer secondsToExpire) {
        log.info("Received request to post {}:{} to the cache", key, value);
        cacheService.putStringKey(key, value, secondsToExpire);
        return new CacheResponse(key, value);
    }

    @DeleteMapping("/{key}")
    public CacheResponse deleteKey(@PathVariable(value = "key") String key) {
        log.info("Received request to delete {} from the cache", key);
        cacheService.deleteKey(key);
        return new CacheResponse(key, null);
    }

}
