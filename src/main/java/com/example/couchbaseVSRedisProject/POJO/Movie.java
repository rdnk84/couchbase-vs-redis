package com.example.couchbaseVSRedisProject.POJO;

public class Movie {

    public String id;
    public String movieName;
    public String movieDescription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", movieDescription='" + movieDescription + '\'' +
                '}';
    }
}
