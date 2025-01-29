package com.example.playground.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.Set;

@ConfigurationProperties(prefix="cache.valkey")
public record CacheProperties(Set<String> nodes, int minIdle) {

}
