package com.example.couchbaseVSRedisProject.restWithPostgres;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void saveMovie(Movie movie) {
        docRepo.save(movie);
    }
}
