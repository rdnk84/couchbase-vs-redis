package com.example.couchbaseVSRedisProject.RedisPostrgesqlAPI;

import com.example.couchbaseVSRedisProject.DocumentNotFoundException;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.search.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/redis-postgres")
public class RedisPostgresController {

    private final RedisService redisService;
    private final PostgresService postgresService;
    private static final Logger logger = LoggerFactory.getLogger(RedisPostgresController.class);

    @Autowired
    public RedisPostgresController(RedisService redisService, PostgresService postgresService) {
        this.redisService = redisService;
        this.postgresService = postgresService;
    }

    @GetMapping("/getMovie/{key}")
    public Movie movieById(@PathVariable(value = "key") String key) throws JsonProcessingException, DocumentNotFoundException {
        Movie retrievedMovie;
        retrievedMovie = redisService.movieByKey(key);
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
            logger.info("Can not process storing to redis");
        }
//        throw new DocumentNotFoundException("No document with ID " + key);
        logger.info("document is not found");
        return null;
    }

    @GetMapping("/movieName")
    public Movie movieByName(@RequestParam(value = "name") String name) throws JsonProcessingException {
        Movie retrievedMovie;
        retrievedMovie = redisService.movieByName(name);
        if (retrievedMovie != null) {
            return retrievedMovie;
        }
        try {
            retrievedMovie = postgresService.findByName(name);
            if (retrievedMovie != null) {
                logger.info("found document is: " + retrievedMovie.getMovieId());
                redisService.saveDocument(retrievedMovie);
                return retrievedMovie;
            }
            logger.info("document is not found");
            return null;
        } catch (JsonProcessingException e) {
            logger.info("Can not process storing to redis");
        }
//        throw new DocumentNotFoundException("No document with ID " + key);
        logger.info("document is not found");
        return null;
    }

    @PostMapping("/feedDB")
    public void feedingDBs(@RequestBody Movie movie) throws JsonProcessingException {
        Movie movieWithId = postgresService.saveMovie(movie);
        redisService.saveDocument(movieWithId);
    }

    @GetMapping("/search/{searchWord}")
    public List<Movie> moviesBySearchWord(@PathVariable(value = "searchWord") String searchWord) throws JsonProcessingException {
        return redisService.documentsByMatch(searchWord);
    }


    @PostMapping("/movie")
    public Movie saveMovie(@RequestBody Movie movie) {
    return postgresService.saveMovie(movie);
    }

//to validate JSON is going to store to DB
    @ExceptionHandler({ JsonMappingException.class })
    public void handleException() {
        System.out.println("тут я чтo-то словила, я хз зачем но мне ооочень нужно");
    }


}


