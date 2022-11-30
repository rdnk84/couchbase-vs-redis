package com.example.couchbaseVSRedisProject.restWithCouchbase;


import com.couchbase.client.java.json.JsonObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couchbase")
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
    public void insertNewDoc(@RequestBody ObjectNode object) {
        mainService.handleUpsertData(object);

    }
}
