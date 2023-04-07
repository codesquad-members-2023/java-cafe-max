package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Primary
@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Long save(final User user) {
        final String sql = "INSERT INTO users (user_id, name, password, email, created_at, updated_at) " +
                "VALUES (:userId, :name, :password, :email, :createdAt, :updatedAt)";

        final SqlParameterSource param = new BeanPropertySqlParameterSource(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(sql, param, keyHolder);

        return (long) Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    @Override
    public Optional<User> findById(final Long id) {
        final String sql  = "SELECT * FROM users WHERE id = :id";

        try {
            return Optional.ofNullable(template.queryForObject(sql, Map.of("id", id), userRowMapper()));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUserId(final String userId) {
        final String sql  = "SELECT * FROM users WHERE user_id = :userId";

        try {
            return Optional.ofNullable(template.queryForObject(sql, Map.of("userId", userId), userRowMapper()));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        final String sql  = "SELECT * FROM users WHERE email = :email";

        try {
            return Optional.ofNullable(template.queryForObject(sql, Map.of("email", email), userRowMapper()));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT * FROM users";

        return template.query(sql, userRowMapper());
    }

    @Override
    public void update(User user) {
        final String sql = "UPDATE users SET user_id=:userId, password=:password WHERE id=:id";

        final MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("password", user.getPassword())
                .addValue("id", user.getId());

        template.update(sql, param);
    }

    private RowMapper<User> userRowMapper() {
        return BeanPropertyRowMapper.newInstance(User.class);
    }

}
