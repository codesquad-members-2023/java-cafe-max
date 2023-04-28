package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Repository
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public void save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("userTable").usingGeneratedKeyColumns("id");
        Map<String, Object> param = new ConcurrentHashMap<>();
        param.put("userId", user.getUserId());
        param.put("password", user.getPassword());
        param.put("email", user.getEmail());
        jdbcInsert.executeAndReturnKey(param); // 이거 지우면 list에 안 나옴(?????????) 어디서 왜 필요한건지
    }

    @Override
    public Optional<User> getUserById(Long id) {
        List<User> result = jdbcTemplate.query("select * from userTable where id = ?", userRowMapper(), id);
        return result.stream().findAny();   // 뭔지 모름
    }

    @Override
    public List<User> getUserList() {
        return jdbcTemplate.query("select * from userTable", userRowMapper());
    } // 이거까지 작동된 듯(3-2)


    @Override
    public void clearStore() {
        jdbcTemplate.update("delete from userTable");
    }

    @Override
    public void update(User user) {
//        String sql = "update userTable set userId=:userId, password=:password; email=:email where id=:id";
//
//        SqlParameterSource param = new MapSqlParameterSource() // 이게 뭐였더라....
//                .addValue("password", user.getPassword())
//                .addValue("userId", user.getUserId())
//                .addValue("email", user.getEmail());
//        jdbcTemplate.update(sql, param); // 되면 다행

        jdbcTemplate.update("UPDATE userTable set password = ? , userId = ? , email = ? where id = ?",
                user.getPassword(), user.getUserId(), user.getEmail(), user.getId()
                );
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUserId(rs.getString("userId"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            return user;
        };
    }
}
