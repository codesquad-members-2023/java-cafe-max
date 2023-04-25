package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class JdbcUserRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    //        @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("Users_squad").usingGeneratedKeyColumns("userId");

        Map<String, Object> parameters = new ConcurrentHashMap<>();
        parameters.put("userNum", user.getUserNum());
        parameters.put("userLoginId", user.getUserLoginId());
        parameters.put("password", user.getPassword());
        parameters.put("email", user.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        user.setUserId(key.longValue()); // 지금 가서 userId 속성 + setter 만들기 (기존의 userId는 userLoginId로 변경)
        return user;
    }
    //        @Override
    public Optional<User> findById(Long id) {
        List<User> result = jdbcTemplate.query("select * from users_squad where userId = ?", userRowMapper(), id);
        return result.stream().findAny();
    }
    //        @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users_squad", userRowMapper());
    }
    public void clearStore() {
        jdbcTemplate.update("delete from users_squad");
    }
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getLong("userId"));
            user.setUserNum(rs.getLong("userNum"));
            user.setUserLoginId(rs.getString("userLoginId"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            return user;
        };
    }
}
