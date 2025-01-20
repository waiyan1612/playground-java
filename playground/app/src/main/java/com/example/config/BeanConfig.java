package com.example.config;

import com.example.GreetingService;
import com.example.NoOpService;
import io.valkey.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class BeanConfig {

    @Bean
    public NoOpService noOpService() {
        return new NoOpService();
    }

    @Bean
    public GreetingService greetingService() {
        return new GreetingService(noOpService());
    }

    @Bean()
    @ConditionalOnProperty(prefix="cache.valkey", value="cluster.enabled", havingValue = "true")
    public JedisCluster jedisCluster(CacheConfig cacheConfig) {
        ConnectionPoolConfig config = new ConnectionPoolConfig();
        config.setMaxTotal(cacheConfig.getMinIdle() * 2);
        config.setMaxIdle(cacheConfig.getMinIdle() * 2);
        config.setMinIdle(cacheConfig.getMinIdle());

        Set<HostAndPort> jedisClusterNodes = cacheConfig.getNodes().stream().map(
                x -> new HostAndPort(x.split(":")[0], Integer.parseInt(x.split(":")[1]))
        ).collect(Collectors.toSet());
        return new JedisCluster(jedisClusterNodes);
    }

    @Bean
    public JedisPool jedisPool(CacheConfig cacheConfig) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(cacheConfig.getMinIdle() * 2);
        config.setMaxIdle(cacheConfig.getMinIdle() * 2);
        config.setMinIdle(cacheConfig.getMinIdle());

        String node = cacheConfig.getNodes().stream().findFirst().orElseThrow();
        return new JedisPool(config, node.split(":")[0], Integer.parseInt(node.split(":")[1]));
    }

}
