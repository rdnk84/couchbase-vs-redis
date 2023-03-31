package com.example.couchbaseVSRedisProject.RedisPostrgesqlAPI;


import com.example.couchbaseVSRedisProject.POJO.Movie;
import com.example.couchbaseVSRedisProject.couchbaseAPI.CouchbaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.json.Path;
import redis.clients.jedis.json.Path2;
import redis.clients.jedis.search.*;

import javax.annotation.PostConstruct;
import java.util.*;
//import java.util.logging.Logger;

@Service
public class RedisService {

    private final UnifiedJedis client;
    private final Schema schema;
    private final IndexDefinition rule;
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    public RedisService(UnifiedJedis client) {
        this.client = client;
        schema = new Schema();
        rule = new IndexDefinition(IndexDefinition.Type.JSON);

    }

    @PostConstruct
    public void init() {
        schema.addTextField("$.movieName", 1.0).addTextField("$.movieDescription", 1.0);
        rule.setPrefixes(new String[]{"movie:"});
        try {
            client.ftCreate("movie-index", IndexOptions.defaultOptions().setDefinition(rule), schema);
        } catch (JedisDataException e) {
            System.out.println("Index movie-index already presented");
        }
    }


    public Movie movieByName(String movieName) throws JsonProcessingException {
        SearchResult movieNameSearch = client.ftSearch("movie-index",
                new Query(movieName));
        ObjectMapper mapper = new ObjectMapper();
        Movie movie;
        Document document = movieNameSearch.getDocuments().get(0);
        if (document != null) {
            for (Map.Entry<String, Object> property : document.getProperties()) {
                String props = property.getValue().toString();
                movie = mapper.readValue(props, Movie.class);
                logger.info("found" + movie.getMovieId());
                return movie;
            }
        }
        logger.info("The document is not found");
        return null;
    }


    public Movie getDocument(String key) throws JsonProcessingException {
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
        logger.info("Saved document: " + key);
        return movie;
    }


}
