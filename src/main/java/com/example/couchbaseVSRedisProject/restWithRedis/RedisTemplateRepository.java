package com.example.couchbaseVSRedisProject.restWithRedis;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

//public class RedisTemplateRepository implements DocumentsRepository{
//
////    private final RedisTemplate<Long, Movie> redisTemplate;
////
////    @Autowired
////    public RedisTemplateRepository(RedisTemplate<Long, Movie> redisTemplate) {
////        this.redisTemplate = redisTemplate;
////    }
////
////
////    @Override
////    public int save(Movie movie) {
////        redisTemplate.opsForValue().set(movie.getId(), movie);
////        return 1;
////    }
////
////    @Override
////    public List<Object> findAll() {
////
////        return redisTemplate.exec();
////    }
////
////    @Override
////    public int update(String updatedDoc) {
////        return 0;
////    }
////
////    @Override
////    public String findById(Long ID) {
////        return null;
////    }
////
////    @Override
////    public int deleteById(Long ID) {
////        return 0;
////    }
////
////
////
////    @Override
////    public List<String> findByDocContaining(String word) {
////        return null;
////    }
////
////    @Override
////    public int deleteAll() {
////        return 0;
////    }
//}
