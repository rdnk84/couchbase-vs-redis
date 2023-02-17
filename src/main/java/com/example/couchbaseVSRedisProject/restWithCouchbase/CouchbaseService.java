package com.example.couchbaseVSRedisProject.restWithCouchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.example.couchbaseVSRedisProject.POJO.POJODoc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

@Service
public class CouchbaseService {

    private final Cluster couchbaseCluster;
    static String bucketName = "Bucket1";

    @Autowired
    public CouchbaseService(Cluster couchbaseCluster) {
        this.couchbaseCluster = couchbaseCluster;
    }

    public Movie saveDocument(Movie movie) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String key = UUID.randomUUID().toString();
        movie.setMovieId(key);
        try {
            Bucket bucket = couchbaseCluster.bucket(bucketName);
            bucket.defaultCollection().upsert(key, movie);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        Movie movieId = new Movie(key);
        Logger.getLogger(this.getClass().getSimpleName()).info("savedDocument: " + movieId.getMovieId());
        return movieId;
    }


    public Movie getDocument(String key) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String resultAsString = null;
        try {
            Bucket bucket = couchbaseCluster.bucket(bucketName);
            GetResult result = bucket.defaultCollection().get(key);
            JsonObject jsonObject = result.contentAsObject();
            resultAsString = jsonObject.toString();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        Movie retrievedDocument = objectMapper.readValue(resultAsString, Movie.class);
        Logger.getLogger(this.getClass().getSimpleName()).info("retrievedDocument: " + retrievedDocument.getMovieId());
        return retrievedDocument;
    }


}
