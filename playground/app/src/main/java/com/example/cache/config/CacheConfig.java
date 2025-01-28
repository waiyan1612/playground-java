package com.example.cache.config;

import io.valkey.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfig {

    @Bean()
    @ConditionalOnProperty(prefix="cache.valkey", value="cluster.enabled", havingValue = "true")
    public JedisCluster jedisCluster(CacheProperties cacheProperties) {
        ConnectionPoolConfig config = new ConnectionPoolConfig();
        config.setMaxTotal(cacheProperties.minIdle() * 2);
        config.setMaxIdle(cacheProperties.minIdle() * 2);
        config.setMinIdle(cacheProperties.minIdle());

        Set<HostAndPort> jedisClusterNodes = cacheProperties.nodes().stream().map(
                x -> new HostAndPort(x.split(":")[0], Integer.parseInt(x.split(":")[1]))
        ).collect(Collectors.toSet());
        return new JedisCluster(jedisClusterNodes);
    }

    @Bean
    public JedisPool jedisPool(CacheProperties cacheProperties) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(cacheProperties.minIdle() * 2);
        config.setMaxIdle(cacheProperties.minIdle() * 2);
        config.setMinIdle(cacheProperties.minIdle());

        String node = cacheProperties.nodes().stream().findFirst().orElseThrow();
        return new JedisPool(config, node.split(":")[0], Integer.parseInt(node.split(":")[1]));
    }

}
