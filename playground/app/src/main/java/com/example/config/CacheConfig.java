package com.example.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
@ConfigurationProperties(prefix="cache.valkey")
public class CacheConfig {

    private final Set<String> nodes = new HashSet<>();

    private int minIdle;

    public Set<String> getNodes() {
        return nodes;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }
}
