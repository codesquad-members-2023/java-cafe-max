package kr.codesquad.cafe.global;

import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.post.PostRepository;
import kr.codesquad.cafe.user.UserRepository;
import kr.codesquad.cafe.user.domain.Role;
import kr.codesquad.cafe.user.domain.User;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

@Profile("cloud")
@Component
public class AppStartupRunner implements CommandLineRunner {

    private static final String ADMIN = "admin";
    private static final String PASSWORD = "W5p8Dbc3f7sOjqaRc7WVp8inlukLB3LJ";
    private static final String EMAIL = "R9gvxFpZK6xYikAdPrdy1mDtd3O+ag2L";
    private static final String TEST_TITLE = "명언";
    private static final int START_NUMBER = 0;
    private static final String CSV_FILE_NAME = "/goodword.csv";
    private static final String USER_DIR = "user.dir";
    private final UserRepository userRepository;
    private final StringEncryptor encryptor;

    private final PostRepository postRepository;

    public AppStartupRunner(UserRepository userRepository, StringEncryptor encryptor, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.encryptor = encryptor;
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) {
        User manager = new User.Builder()
                .password(PASSWORD)
                .email(encryptor.decrypt(EMAIL))
                .nickname(ADMIN)
                .role(Role.MANAGER).build();
        User savedManager = userRepository.save(manager);


        String path = System.getProperty(USER_DIR) + CSV_FILE_NAME;
        System.out.println(path);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int index = START_NUMBER;
            while ((line = br.readLine()) != null) {
                Post test = new Post.Builder()
                        .createdDateTime(LocalDateTime.now())
                        .title(TEST_TITLE + (++index))
                        .user(savedManager)
                        .textContent(line)
                        .nickname(manager.getNickname())
                        .build();
                postRepository.save(test);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
