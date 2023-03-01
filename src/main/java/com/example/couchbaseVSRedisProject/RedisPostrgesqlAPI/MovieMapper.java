package com.example.couchbaseVSRedisProject.RedisPostrgesqlAPI;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieMapper implements ResultSetExtractor {
    @Override
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
        Movie movie = new Movie();
        movie.setMovieId(rs.getString("id"));
        movie.setMovieName(rs.getString("name"));
        movie.setMovieDescription(rs.getString("description"));
        return movie;
    }
}
