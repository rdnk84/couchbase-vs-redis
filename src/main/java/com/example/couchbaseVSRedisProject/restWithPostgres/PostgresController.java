package com.example.couchbaseVSRedisProject.restWithPostgres;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postgres")
public class PostgresController {

    private final PostgresService postgresService;

    @Autowired
    public PostgresController(PostgresService postgresService) {
        this.postgresService = postgresService;
    }


    @GetMapping("/allMovies")
    public List<Movie> allMovies() {

        return postgresService.getAllMovies();
    }

    @PostMapping("/addMovie")
    public void saveMovie(@RequestBody Movie movie) {
        postgresService.saveMovie(movie);
    }

}
