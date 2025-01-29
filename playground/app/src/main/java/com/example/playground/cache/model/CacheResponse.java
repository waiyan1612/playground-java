package com.example.playground.cache.model;

import java.util.Optional;

public record CacheResponse(String key, String value, Optional<Long> expires) {
}
