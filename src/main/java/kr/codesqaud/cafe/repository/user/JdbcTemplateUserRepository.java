package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {
    // 원래는 JdbcTemplate을 사용했지만
    // NamedParameterJdbcTemplate와 SimpleJdbcInsert을 동시에 사용하도록 변경
    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcTemplateUserRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(user);
        Number key = simpleJdbcInsert.executeAndReturnKey(param);
        user.setId(key.longValue());
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "select ID, USERID, PASSWORD, NAME, EMAIL from USERS where ID = :id";

        try {
            Map<String, Object> param = Map.of("id", id);
            User user = template.queryForObject(sql, param, userRowMapper());
            assert user != null;
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        String sql = "select ID, USERID, PASSWORD, NAME, EMAIL from USERS where USERID = :userId";

        try {
            Map<String, Object> param = Map.of("userId", userId);
            User user = template.queryForObject(sql, param, userRowMapper());
            assert user != null;
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from users";
        return template.query(sql, userRowMapper());
    }

    @Override
    public void update(Long id, User updateUser) {
        String sql = "update USERS " +
                "set PASSWORD=:password, NAME=:name, EMAIL=:email " +
                "where ID=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("password", updateUser.getPassword())
                .addValue("name", updateUser.getName())
                .addValue("email", updateUser.getEmail())
                .addValue("id", id);

        template.update(sql, param);
    }

    // TODO: 이대로 메서드로 두는 것이 좋은지, 필드로 빼는 것이 좋은지 판단 후 재구현
    private RowMapper<User> userRowMapper() {
        return BeanPropertyRowMapper.newInstance(User.class);
    }
}
