package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getUserId();
    }

    private void validateDuplicateUser(User user) {
        userRepository.findById(user.getUserId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다");
                });
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(String userId) {
        return userRepository.findById(userId);
    }
}
