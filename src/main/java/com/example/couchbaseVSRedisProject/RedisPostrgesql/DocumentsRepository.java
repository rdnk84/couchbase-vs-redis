package com.example.couchbaseVSRedisProject.RedisPostrgesql;

import com.example.couchbaseVSRedisProject.POJO.Movie;

import java.util.List;

public interface DocumentsRepository {

    int save(Movie movie);

    int update(String updatedDoc);

    String findById(Long ID);

    int deleteById(Long ID);

    List<Object> findAll();

    List<String> findByDocContaining(String word);

    int deleteAll();
}
