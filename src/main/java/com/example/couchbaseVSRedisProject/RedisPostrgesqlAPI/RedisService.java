package com.example.couchbaseVSRedisProject.RedisPostrgesqlAPI;


import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        String requestedMovie;
        try (Jedis jedis = jedisPool.getResource()) {
            requestedMovie = jedis.get(key);
        }
        if (requestedMovie != null) {
            return mapper.readValue(requestedMovie, Movie.class);
        }
        return null;
    }

    public Movie saveDocument(Movie movie) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String key = UUID.randomUUID().toString();
        movie.setMovieId(key);
        String movieAsJsonString = mapper.writeValueAsString(movie);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, movieAsJsonString);
        } catch (Exception e) {
            System.out.println("caught exception: " + e.getMessage());
        }
        Movie movieId = new Movie(key);
        Logger.getLogger(this.getClass().getSimpleName()).info("Saved document: " + movieId.getMovieId());
        return movieId;
    }

    public void removeDocument(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.getLogger(this.getClass().getSimpleName()).info("Document is deleted");
    }
}
