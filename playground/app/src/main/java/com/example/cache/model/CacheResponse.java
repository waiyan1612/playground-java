package com.example.cache.model;

import java.util.Optional;

public record CacheResponse(String key, String value, Optional<Long> expires) {
}
