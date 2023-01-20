package com.example.couchbaseVSRedisProject.restWithPostgres;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JDBCTemplateDocRepo implements DocumentsRepository {

    private JdbcTemplate jdbcTemplate;

    RowMapper<Movie> rowMapper = ((rs, rowNum) -> {
        Movie movie = new Movie();
        movie.setId(rs.getString("id"));
        movie.setMovieName(rs.getString("name"));
        movie.setMovieDescription("description");
        return movie;
    });

    @Autowired
    public JDBCTemplateDocRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Movie movie) {
        String sql = "INSERT into movies (name, description) values(?,?)";
        return jdbcTemplate.update(sql, movie.getMovieName(), movie.getMovieDescription());
    }

    @Override
    public int update(String updatedDoc) {
        return 0;
    }



    @Override
    public Movie findById(String ID) {
//        Integer id = Integer.parseInt(ID);
        return jdbcTemplate.query("SELECT * FROM Movies WHERE id=?", new Object[]{ID}, new BeanPropertyRowMapper<>(Movie.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public int deleteById(Long ID) {
        return 0;
    }

    @Override
    public List<Movie> findAll() {
        String sql = "SELECT * from movies";
        return jdbcTemplate.query(sql, rowMapper);
//        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Movie.class));
    }

    @Override
    public List<String> findByDocContaining(String word) {
        return null;
    }

    @Override
    public int deleteAll() {
        return 0;
    }


}
