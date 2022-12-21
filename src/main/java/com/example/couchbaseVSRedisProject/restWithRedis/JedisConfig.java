package com.example.couchbaseVSRedisProject.restWithRedis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
public class JedisConfig {

//    @Bean
//    JedisConnectionFactory jedisConnectionFactory() {
//        return new JedisConnectionFactory();
//    }

       public JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    final JedisPoolConfig poolConfig = buildPoolConfig();

    //    JedisPool jedisPool = new JedisPool(poolConfig, "localhost");
    @Bean
    JedisPool jedisPool() {
        return new JedisPool(poolConfig, "localhost");
    }
}
