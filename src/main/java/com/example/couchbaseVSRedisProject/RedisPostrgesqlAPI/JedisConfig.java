package com.example.couchbaseVSRedisProject.RedisPostrgesqlAPI;

import org.apache.commons.pool2.PooledObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.*;
import redis.clients.jedis.providers.PooledConnectionProvider;

import java.time.Duration;

@Configuration
public class JedisConfig {



    @Value("${spring.redis.host:localhost}")
    private String host;
    @Value("${spring.redis.port:12346}")
    private int port;


    @Bean
    UnifiedJedis client() {
        HostAndPort config = new HostAndPort(host, port);
        PooledConnectionProvider provider = new PooledConnectionProvider(config);
        return new UnifiedJedis(provider);
    }
}
