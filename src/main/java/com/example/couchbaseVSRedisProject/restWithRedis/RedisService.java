package com.example.couchbaseVSRedisProject.restWithRedis;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.example.couchbaseVSRedisProject.restWithPostgres.PostgresService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.serialize.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;
import java.util.logging.Logger;

@Service
public class RedisService {

    private final JedisPool jedisPool;

    @Autowired
    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public Movie getDocument(String key) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String requestedMovie = null;
        try (Jedis jedis = jedisPool.getResource()) {
            requestedMovie = jedis.get(key);
        }
        Movie retrievedDocument = mapper.readValue(requestedMovie, Movie.class);
        return retrievedDocument;
    }

    public Movie saveDocument(Movie movie) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String key = UUID.randomUUID().toString();
        movie.setId(key);
        String movieAsJsonString = mapper.writeValueAsString(movie);
        String retrieveDocument = null;
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, movieAsJsonString);
            retrieveDocument = jedis.get(key);

        } catch (Exception e) {
            System.out.println("caught exception: " + e.getMessage());
        }
        Logger.getLogger(this.getClass().getSimpleName()).info("retrievedDocument: " + retrieveDocument);
        Movie retrievedDocument = mapper.readValue(retrieveDocument, Movie.class);
        return retrievedDocument;

    }
}
