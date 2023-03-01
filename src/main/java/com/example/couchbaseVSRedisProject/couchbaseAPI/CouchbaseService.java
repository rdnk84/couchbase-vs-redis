package com.example.couchbaseVSRedisProject.couchbaseAPI;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.manager.query.QueryIndexManager;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class CouchbaseService {

    private final Cluster couchbaseCluster;
    static String bucketName = "Movies";

    @Autowired
    public CouchbaseService(Cluster couchbaseCluster) {
        this.couchbaseCluster = couchbaseCluster;
    }


    public Movie saveDocument(Movie movie) throws JsonProcessingException {
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


    public Movie getDocumentByKey(String key) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String resultAsString = null;
        try {
            GetResult result = couchbaseCluster.bucket(bucketName).defaultCollection().get(key);
            JsonObject jsonObject = result.contentAsObject();
            resultAsString = jsonObject.toString();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        Movie retrievedDocument = objectMapper.readValue(resultAsString, Movie.class);
        Logger.getLogger(this.getClass().getSimpleName()).info("retrievedDocument: " + retrievedDocument.getMovieId());
        return retrievedDocument;
    }

    public Movie getDocumentBySearch(String searchWord) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Movie movie = new Movie();
        try {
            QueryResult result = couchbaseCluster.query("select t.* from  Movies as t where movieName = ?",
                    QueryOptions.queryOptions().parameters(JsonArray.from(searchWord)));
            for (JsonObject row : result.rowsAsObject()) {
                movie = objectMapper.readValue(row.toString(), Movie.class);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        Logger.getLogger(this.getClass().getSimpleName()).info("retrievedDocument: " + movie.toString());
        return movie;
    }


}
