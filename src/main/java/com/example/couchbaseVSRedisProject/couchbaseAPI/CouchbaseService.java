package com.example.couchbaseVSRedisProject.couchbaseAPI;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.manager.query.QueryIndexManager;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.search.queries.MatchPhraseQuery;
import com.couchbase.client.java.search.result.SearchResult;
import com.couchbase.client.java.search.result.SearchRow;
import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
//import java.util.logging.Logger;

@Service
public class CouchbaseService {

    private static final Logger logger = LoggerFactory.getLogger(CouchbaseService.class);

    private final Cluster couchbaseCluster;
    static String bucketName = "Movies";
     ;

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
        logger.info("savedDocument: " + movie.getMovieId());
        return movieId;
    }


    public Movie getDocumentByKey(String key) throws JsonProcessingException {
        String resultAsString = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            GetResult result = couchbaseCluster.bucket(bucketName).defaultCollection().get(key);
            JsonObject jsonObject = result.contentAsObject();
            resultAsString = jsonObject.toString();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        Movie retrievedDocument = objectMapper.readValue(resultAsString, Movie.class);
        logger.info("retrievedDocument: " + retrievedDocument.getMovieId());
        return retrievedDocument;
    }

    public Movie getDocumentByName(String movieName) throws JsonProcessingException {
        Movie movie = new Movie();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            QueryResult result = couchbaseCluster.query("select t.* from  Movies as t where movieName = ?",
                    QueryOptions.queryOptions().parameters(JsonArray.from(movieName)));
            for (JsonObject row : result.rowsAsObject()) {
                movie = objectMapper.readValue(row.toString(), Movie.class);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        logger.info("retrievedDocument: " + movieName.toString());
        return movie;
    }


    public List<Movie> getDocumentByMatch(String searchWord) {
        Movie movie;
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Movie> allDocuments = new ArrayList<>();
        try {
            SearchResult result = couchbaseCluster.searchQuery("movs", new MatchPhraseQuery(searchWord));
            for (SearchRow row : result.rows()) {
                GetResult value = couchbaseCluster.bucket(bucketName).defaultCollection().get(row.id());
                JsonObject jsonObject = value.contentAsObject();
                String resultAsString = jsonObject.toString();
                movie = objectMapper.readValue(resultAsString, Movie.class);
                allDocuments.add(movie);
                logger.info("Found movie: " + movie.getMovieName());
            }
            return allDocuments;
        } catch (Exception e) {
            logger.info(e.getMessage() + " happened");
        }
        return null;
    }

}
