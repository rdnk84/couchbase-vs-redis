package com.example.couchbaseVSRedisProject.POJO;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.UUID;

public class Movie  {

    public String movieID;
    public String movieName;
    public String movieDescription;

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
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
        return "Movie{" +
                "id=" + movieID +
                ", movieName='" + movieName + '\'' +
                ", movieDescription='" + movieDescription + '\'' +
                '}';
    }
}
