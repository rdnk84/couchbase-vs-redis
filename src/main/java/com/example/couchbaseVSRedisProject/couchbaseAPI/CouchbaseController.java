package com.example.couchbaseVSRedisProject.couchbaseAPI;


import com.couchbase.client.java.json.JsonObject;
import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/couchbase")
public class CouchbaseController {

    private final CouchbaseService couchbaseService;

    @Autowired
    public CouchbaseController(CouchbaseService mainService) {
        this.couchbaseService = mainService;
    }

    @GetMapping("/getMovie/{key}")
    public Movie retrieveDocByKey(@PathVariable(value = "key") String key) throws JsonProcessingException {
        return couchbaseService.getDocumentByKey(key);
    }

    @PostMapping("/movie")
    public Movie saveDocument(@RequestBody Movie movie) throws JsonProcessingException {
        return couchbaseService.saveDocument(movie);
    }

    @GetMapping("/movieName/{name}")
    public Movie retrieveDocByName(@PathVariable(value = "name") String movieName) throws JsonProcessingException {
        return couchbaseService.getDocumentByName(movieName);
    }

    @GetMapping("/search")
    public List<Movie> retrieveDocBySearchWord(@RequestParam(value="searchWord") String searchWord) throws JsonProcessingException {
        return couchbaseService.getDocumentByMatch(searchWord);
    }
}
