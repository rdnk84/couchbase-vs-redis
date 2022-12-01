package com.example.couchbaseVSRedisProject.restWithCouchbase;


import com.couchbase.client.java.json.JsonObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/couchbase")
public class CouchbaseController {

    private final CouchbaseService mainService;

    @Autowired
    public CouchbaseController(CouchbaseService mainService) {
        this.mainService = mainService;
    }

    @GetMapping("/{id}")
    public JsonObject retrieveDocByKey(@PathVariable String docID) {

        return mainService.retrievingDoc(docID);

    }

    @PostMapping("/postData")
    public POJODoc insertNewDoc(@RequestBody ObjectNode object) throws JsonProcessingException {
        return mainService.handleUpsertData(object);

    }

//    @PostMapping("/postData")
//    public String insertNewDoc(@RequestBody ObjectNode object)  {
//        return mainService.handleUpsertData(object);
//
//    }
}
