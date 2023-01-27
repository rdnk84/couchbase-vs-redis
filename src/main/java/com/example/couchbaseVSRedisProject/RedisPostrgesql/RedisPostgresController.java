package com.example.couchbaseVSRedisProject.RedisPostrgesql;

import com.example.couchbaseVSRedisProject.DocumentNotFoundException;
import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Movie getDocument(@PathVariable(value = "key") String key) throws JsonProcessingException, DocumentNotFoundException {
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
        return null;
    }

    @PostMapping("/movie")
    public void saveMovie(@RequestBody Movie movie) {
        postgresService.saveMovie(movie);
    }

    @PatchMapping("/movie/{key}")
    public void updateMovie(@RequestBody Movie movie, @PathVariable(value = "key") String key) {
        redisService.removeDocument(key);
        postgresService.updateMovie(movie, key);
    }

}


