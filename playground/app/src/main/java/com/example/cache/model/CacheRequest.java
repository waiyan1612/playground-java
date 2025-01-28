package com.example.cache.model;

import java.util.Optional;

public record CacheRequest(String value, Optional<Long> ttl) {
}
