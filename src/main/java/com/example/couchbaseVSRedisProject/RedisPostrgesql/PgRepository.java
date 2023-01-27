package com.example.couchbaseVSRedisProject.RedisPostrgesql;

import com.example.couchbaseVSRedisProject.POJO.Movie;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PgRepository {

    List<Movie> findAll();

    int save(Movie movie);

    int update(Movie movie, String ID);

    int deleteById(String ID);

    List<String> findByDocContaining(String word);

    int deleteAll();

    Movie findById(String ID);



}
