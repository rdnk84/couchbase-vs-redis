package com.example.couchbaseVSRedisProject.RedisPostrgesqlAPI;

import com.example.couchbaseVSRedisProject.DocumentNotFoundException;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.search.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/redis-postgres")
public class RedisPostgresController {

    private final RedisService redisService;
    private final PostgresService postgresService;

    @Autowired
    public RedisPostgresController(RedisService redisService, PostgresService postgresService) {
        this.redisService = redisService;
        this.postgresService = postgresService;
    }

    @GetMapping("/getMovie/{key}")
    public Movie movieById(@PathVariable(value = "key") String key) throws JsonProcessingException, DocumentNotFoundException {
        Movie retrievedMovie;
        retrievedMovie = redisService.getDocument(key);
        if (retrievedMovie != null) {
            return retrievedMovie;
        }
        try {
            retrievedMovie = postgresService.findById(key);
            if (retrievedMovie != null) {
                redisService.saveDocument(retrievedMovie);
                return retrievedMovie;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Logger.getLogger(this.getClass().getSimpleName()).info("Can not process saving in redis");
        }
//        throw new DocumentNotFoundException("No document with ID " + key);
        Logger.getLogger(this.getClass().getSimpleName()).info("not found");
        return null;
    }

    @GetMapping("/movieName/{movieName}")
    public Movie movieByName(@PathVariable(value = "movieName") String movieName) throws JsonProcessingException {
        Movie retrievedMovie;
        retrievedMovie = redisService.movieByName(movieName);
        if (retrievedMovie != null) {
            return retrievedMovie;
        }
        try {
            retrievedMovie = postgresService.findByName(movieName);
            if (retrievedMovie != null) {
                redisService.saveDocument(retrievedMovie);
                return retrievedMovie;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Logger.getLogger(this.getClass().getSimpleName()).info("Can not process saving in redis");
        }
//        throw new DocumentNotFoundException("No document with ID " + key);
        Logger.getLogger(this.getClass().getSimpleName()).info("Get document id: " + retrievedMovie.getMovieId());
        return null;

    }

    @GetMapping("/testforredis/{movieName}")
    public void testForRedis(@PathVariable(value = "movieName") String movieName) throws JsonProcessingException {
         redisService.movieByName(movieName);
    }

    @PostMapping("/movie")
    public Movie saveMovie(@RequestBody Movie movie) {
        return postgresService.saveMovie(movie);
    }


//    @PatchMapping("/movie/{key}")
//    public void updateMovie(@RequestBody Movie movie, @PathVariable(value = "key") String key) {
//        redisService.removeDocument(key);
//        postgresService.updateMovie(movie, key);
//    }


}


