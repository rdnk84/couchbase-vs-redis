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


@Service
public class RedisService {

    private final UnifiedJedis client;
    private final Schema schema;
    private final IndexDefinition indexDefinition;
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    public RedisService(UnifiedJedis client) {
        this.client = client;
        schema = new Schema();
        indexDefinition = new IndexDefinition(IndexDefinition.Type.JSON);
    }

    @PostConstruct
    public void idxDefinition() {
        schema.addField(new Schema.Field(FieldName.of("$.movieName").as("name"), Schema.FieldType.TEXT, true, false))
                .addField(new Schema.Field(FieldName.of("$.movieDescription").as("description"), Schema.FieldType.TEXT, true, false));
        indexDefinition.setPrefixes(new String[]{"movie:"});
        try {
            client.ftCreate("movie-index", IndexOptions.defaultOptions().setDefinition(indexDefinition), schema);
        } catch (JedisDataException e) {
            logger.info("Secondary index exists already");
        }
    }


    public Movie movieByName(String name) throws JsonProcessingException {
        SearchResult movieNameSearch = client.ftSearch("movie-index",
                new Query(name));
        ObjectMapper mapper = new ObjectMapper();
        Movie movie;
        Query query = new Query(name);

        if (movieNameSearch.getDocuments().size() != 0) {
            Document document = movieNameSearch.getDocuments().get(0);
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


    public Movie movieByKey(String key) throws JsonProcessingException {
        Movie movie;
        String movieKey = "movie:" + key;
        movie = client.jsonGet(movieKey, Movie.class);
        if (movie != null) {
            return movie;
        }
        return null;
    }

    public List<Movie> documentsByMatch(String word) throws JsonProcessingException {
        Movie movie;
        SearchResult movieNameSearch = client.ftSearch("movie-index", new Query(word));
        List<Document> documents = movieNameSearch.getDocuments();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Movie> moviesFound = new ArrayList<>();
        if (documents != null) {
            for (int i = 0; i < documents.size(); i++) {
                Document document = movieNameSearch.getDocuments().get(i);
                for (Map.Entry<String, Object> property : document.getProperties()) {
                    String props = property.getValue().toString();
                    movie = mapper.readValue(props, Movie.class);
                    moviesFound.add(movie);
                    logger.info("found" + movie.getMovieId());
                }
            }
            return moviesFound;
        }
        logger.info("The document is not found");
        return null;
    }


    public Movie saveDocument(Movie movie) throws JsonProcessingException {
        String key = "movie:" + movie.getMovieId();
        client.jsonSet(key, Path.ROOT_PATH, movie);
        logger.info("Saved document: " + key);
        return movie;
    }


}
