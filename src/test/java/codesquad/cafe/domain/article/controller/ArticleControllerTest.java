package codesquad.cafe.domain.article.controller;

import codesquad.cafe.domain.article.repository.MemoryArticleRepository;
import codesquad.cafe.domain.user.repository.MemoryUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemoryArticleRepository articleRepository;

    @Test
    @DisplayName("[GET] / 로 이동하면 게시글 목록 가져와서 index 화면에 출력하기 테스트")
    void showHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("posts"))
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    void writePost() {
    }

    @Test
    void showDetailPost() {
    }
}