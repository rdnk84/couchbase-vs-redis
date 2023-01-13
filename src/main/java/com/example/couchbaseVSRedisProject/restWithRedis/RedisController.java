package com.example.couchbaseVSRedisProject.restWithRedis;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.example.couchbaseVSRedisProject.restWithPostgres.PostgresService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Movie getDocument(@PathVariable(value = "key") String key) throws JsonProcessingException {
        return redisService.getDocument(key);
    }

    @PostMapping("/sendMovie")
    public Movie saveDocument(@RequestBody Movie movie) throws JsonProcessingException {
       return redisService.saveDocument(movie);
    }

    public void saveBatchOfDocs(){
        //
    }
}
