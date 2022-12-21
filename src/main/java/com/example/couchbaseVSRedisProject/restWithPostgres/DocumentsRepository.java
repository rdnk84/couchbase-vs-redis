package com.example.couchbaseVSRedisProject.restWithPostgres;

import com.example.couchbaseVSRedisProject.POJO.Movie;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentsRepository {

int save(Movie movie);

int update(String updatedDoc);

String findById(Long ID);

int deleteById(Long ID);

//List<String> findAll();
List<Movie> findAll();

List<String> findByDocContaining(String word);

int deleteAll();

}
