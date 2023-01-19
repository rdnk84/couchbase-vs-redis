package com.example.couchbaseVSRedisProject.restWithRedis;

import com.example.couchbaseVSRedisProject.DocumentNotFoundException;
import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.example.couchbaseVSRedisProject.restWithPostgres.PostgresService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    private final RedisService redisService;
    private final PostgresService postgresService;

    @Autowired
    public RedisController(RedisService redisService, PostgresService postgresService) {
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
            retrievedMovie = postgresService.getMovie(key);
            if (retrievedMovie != null) {
                redisService.saveDocument(retrievedMovie);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Logger.getLogger(this.getClass().getSimpleName()).info("Can not process saving in redis");
            retrievedMovie = null;
        }
        if (retrievedMovie != null) {
            return retrievedMovie;
        }
        throw new DocumentNotFoundException("Документ с номером " + key + "не найден");
    }

//    @GetMapping("/getMovie/{key}")
//    public Movie getDocument(@PathVariable(value = "key") String key) throws JsonProcessingException, DocumentNotFoundException {
//
//        Movie retrievedMovie = redisService.getDocument(key);
//
//        if (retrievedMovie == null) {
//            try {
//                retrievedMovie = postgresService.getMovie(key);
//                if(retrievedMovie != null) {
//                    redisService.saveDocument(retrievedMovie);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                Logger.getLogger(this.getClass().getSimpleName()).info("no a Movie with such ID");
//            }
//            return retrievedMovie;
//        } else
//            throw new DocumentNotFoundException("Документ с номером" + " " + key + "не найден");
//
//
//    }


    @PostMapping("/sendMovie")
    public Movie saveDocument(@RequestBody Movie movie) throws JsonProcessingException {
        return redisService.saveDocument(movie);
    }
}


