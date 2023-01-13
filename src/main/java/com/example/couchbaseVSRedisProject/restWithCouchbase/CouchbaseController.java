package com.example.couchbaseVSRedisProject.restWithCouchbase;


import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.example.couchbaseVSRedisProject.POJO.POJODoc;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return couchbaseService.getDocument(key);
    }

    @PostMapping("/saveMovie/postData")
    public Movie saveDocument(@RequestBody Movie movie) throws JsonProcessingException {
        return couchbaseService.saveDocument(movie);
    }

}
