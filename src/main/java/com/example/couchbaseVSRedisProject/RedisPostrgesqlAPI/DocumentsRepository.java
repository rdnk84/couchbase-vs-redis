package com.example.couchbaseVSRedisProject.RedisPostrgesqlAPI;

import com.example.couchbaseVSRedisProject.POJO.Movie;

import java.util.List;

public interface DocumentsRepository {

    int save(Movie movie);

    int update(String updatedDoc);

    String findById(Long Id);

    int deleteById(Long Id);

    List<Object> findAll();

    List<String> findByDocContaining(String word);

    int deleteAll();
}
