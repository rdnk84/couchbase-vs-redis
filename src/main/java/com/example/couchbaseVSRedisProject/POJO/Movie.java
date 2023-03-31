package com.example.couchbaseVSRedisProject.POJO;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.UUID;


public class Movie {

    public String movieId;
    public String movieName;
    public String movieDescription;


    public Movie() {
    }

    public Movie(String movieId) {
        this.movieId = movieId;
    }


    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieID) {
        this.movieId = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    @Override
    public String toString() {
        return "{" +
                "\"movieId\":" + movieId + "," +
                ", \"movieName\":\"" + movieName + "\"," +
                ", \"movieDescription\":\"" + movieDescription + '\"' +
                '}';
    }


}
