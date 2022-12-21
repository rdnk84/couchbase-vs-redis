package com.example.couchbaseVSRedisProject.restWithRedis;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/{keyOfDocument}")
    public String getDocument(@PathVariable(value = "keyOfDocument") String keyOfDocument) {
        return redisService.getDocument();
    }

    @PostMapping("/")
    public void saveDocument(@RequestBody Movie movie) {

        //Movie -> jsonString
//        String jsonString = "";
        redisService.saveDocument(movie);

    }
}
