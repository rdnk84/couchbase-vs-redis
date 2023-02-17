package com.example.couchbaseVSRedisProject.RedisPostrgesql;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PostgresService {

    private final JDBCTemplateDocRepo docRepo;

    @Autowired
    public PostgresService(JDBCTemplateDocRepo docRepo) {
        this.docRepo = docRepo;
    }

    public List<Movie> getAllMovies() {
        return docRepo.findAll();
    }

    public Movie findById(String id) {
        return docRepo.findById(id);
    }

    public Movie saveMovie(Movie movie) {
        String key = UUID.randomUUID().toString();
        movie.setMovieId(key);
        docRepo.save(movie);
        Movie movieId = new Movie(key);
        Logger.getLogger(this.getClass().getSimpleName()).info("Saved document id: " + movieId.getMovieId());
        return movieId;
    }

    public Movie updateMovie(Movie movie, String id) {
        docRepo.update(movie, id);
        return findById(id);
    }
}