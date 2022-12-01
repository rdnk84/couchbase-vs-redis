package com.example.couchbaseVSRedisProject.restWithCouchbase;

import java.util.ArrayList;

public class POJODoc {

    public String name;
    public ArrayList<Movie> movies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}
