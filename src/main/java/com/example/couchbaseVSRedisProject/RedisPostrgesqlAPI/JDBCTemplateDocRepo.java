package com.example.couchbaseVSRedisProject.RedisPostrgesqlAPI;

import com.example.couchbaseVSRedisProject.POJO.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
//import java.util.logging.Logger;

@Repository
public class JDBCTemplateDocRepo implements PgRepository {

    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(JDBCTemplateDocRepo.class);

    RowMapper<Movie> rowMapper = ((rs, rowNum) -> {
        Movie movie = new Movie();
        movie.setMovieId(rs.getString("id"));
        movie.setMovieName(rs.getString("name"));
        movie.setMovieDescription(rs.getString("description"));
        return movie;
    });

    @Autowired
    public JDBCTemplateDocRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Movie> findAll() {
        return jdbcTemplate.query("SELECT id, name, description FROM movies", rowMapper);
//        return jdbcTemplate.query("SELECT * FROM movies WHERE id='555'", new Object[]{}, new BeanPropertyRowMapper<>(Movie.class))
//                .stream().findAny().orElse(null);
    }

    @Override
    public Movie findById(String id) {
        return jdbcTemplate.queryForObject("SELECT id, name, description FROM movies WHERE id=?", rowMapper, id);
//        return jdbcTemplate.query("SELECT * FROM movies WHERE id='555'", new Object[]{}, new BeanPropertyRowMapper<>(Movie.class))
//                .stream().findAny().orElse(null);
    }

    @Override
    public Movie findByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT id, name, description FROM movies WHERE name=?", rowMapper, name);
        } catch (EmptyResultDataAccessException e) {
            logger.info("Movie is not found");
            return null;
        }
    }

    @Override
    public int save(Movie movie) {
        String sql = "INSERT into movies (id, name, description) values(?,?,?)";
        return jdbcTemplate.update(sql, movie.movieId, movie.getMovieName(), movie.getMovieDescription());
    }

    @Override
    public int update(Movie movie, String ID) {
        String sql = "UPDATE movies SET name = ?, description = ? where id=?";
        int update = jdbcTemplate.update(sql, movie.getMovieName(), movie.getMovieDescription(), ID);
        if (update == 1) {
            logger.info("Movie is updated");
//            Logger.getLogger(this.getClass().getSimpleName()).info("Movie is updated");
        }
        return update;
    }


    @Override
    public int deleteById(String ID) {
        return 0;
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
