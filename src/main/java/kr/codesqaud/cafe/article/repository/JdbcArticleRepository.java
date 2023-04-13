package kr.codesqaud.cafe.article.repository;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticleFormDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class JdbcArticleRepository implements ArticleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcArticleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long save(Article article) {
        String sql = "INSERT INTO articles (author, title, contents, time) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, article.getAuthor());
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getContents());
            ps.setTimestamp(4, Timestamp.valueOf(article.getTime()));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }


    public List<Article> findAll() {
        String sql = "SELECT * FROM articles";
        return jdbcTemplate.query(sql, articleRowMapper());
    }

    public Article findByID(Long index) {
        String sql = "SELECT * FROM articles WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, articleRowMapper(), index);
    }

    @Override
    public Long modify(long id, ArticleFormDto articleFormDto) {
        String sql = "UPDATE articles SET title=?, contents=?, time=? WHERE id=?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, articleFormDto.getTitle());
            ps.setString(2, articleFormDto.getContents());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); //repository 역할이 아니다 -> service
            ps.setLong(4, id);
            return ps;
        });
        return id;
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM articles WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article(rs.getString("author"), rs.getString("title"),rs.getString("contents"),rs.getLong("id"),rs.getTimestamp("time"));
            return article;
        };
    }
}

