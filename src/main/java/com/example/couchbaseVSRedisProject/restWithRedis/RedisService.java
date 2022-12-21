package com.example.couchbaseVSRedisProject.restWithRedis;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.UUID;
import java.util.logging.Logger;

@Service
public class RedisService {

    private final JedisPool jedisPool;

    @Autowired
    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String getDocument() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.get("");
        }
        return null;
    }

    public void saveDocument(Movie movie) {
        UUID uuid = UUID.randomUUID();
        String newIDGenerated = uuid.toString();
        String jsonDocument = movie.toString();

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(newIDGenerated, jsonDocument);
        } catch (Exception e) {
            Logger.getLogger("caught exception: " + e.getMessage());
        }
    }
}
