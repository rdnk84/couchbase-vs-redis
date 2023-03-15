package com.example.couchbaseVSRedisProject.RedisPostrgesqlAPI;


import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.json.Path;
import redis.clients.jedis.json.Path2;
import redis.clients.jedis.search.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class RedisService {

    private final UnifiedJedis client;
    private final Schema schema;
    private final IndexDefinition rule;

    @Autowired
    public RedisService(UnifiedJedis client) {
        this.client = client;
        schema = new Schema();
        rule = new IndexDefinition(IndexDefinition.Type.JSON);

    }

    @PostConstruct
    public void init() {
        schema.addTextField("$.movieName", 1.0);
        rule.setPrefixes(new String[]{"movie:"});
        try {
            client.ftCreate("movie-index", IndexOptions.defaultOptions().setDefinition(rule), schema);
        } catch (JedisDataException e) {
            System.out.println("Index movie-index already presented");
        }
    }


    public void movieByName(String movieName) throws JsonProcessingException {
        SearchResult movieNameSearch = client.ftSearch("movie-index",
//                new Query("@\\$\\" + ".movieName:movieName*"));
                new Query("." + "movieName"));
        List<Document> docs = movieNameSearch.getDocuments();
        if (docs != null) {
            for (Document movieItem : docs) {

                movieItem.getProperties();
                System.out.println("bla");
            }
            Logger.getLogger(this.getClass().getSimpleName()).info("The document is not found: ");
        }
    }

    public Movie getDocument(String key) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Movie movie;
        String movieKey = "movie:" + key;
        movie = client.jsonGet(movieKey, Movie.class);
        if (movie != null) {
            return movie;
        }
        return null;
    }


    public Movie saveDocument(Movie movie) throws JsonProcessingException {
        String key = "movie:" + movie.getMovieId();
        client.jsonSet(key, Path.ROOT_PATH, movie);
        Logger.getLogger(this.getClass().getSimpleName()).info("Saved document: " + key);
        return movie;
    }

//    public void removeDocument(String key) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.del(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Logger.getLogger(this.getClass().getSimpleName()).info("Document is deleted");
//    }


}
