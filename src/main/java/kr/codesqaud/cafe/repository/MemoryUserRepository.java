package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static final Map<String, User> USER_REPOSITORY = new HashMap<>();

    @Override
    public User save(User user) {
        // TODO: 유효성 검증 로직 추가
        USER_REPOSITORY.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findByID(String id) {
        return Optional.ofNullable(USER_REPOSITORY.get(id));
    }

    @Override
    public Optional<User> findByName(String name) {
        return USER_REPOSITORY.values().stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(USER_REPOSITORY.values());
    }

    public void clear() {
        USER_REPOSITORY.clear();
    }
}
