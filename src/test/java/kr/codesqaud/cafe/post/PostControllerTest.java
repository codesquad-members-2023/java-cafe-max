package kr.codesqaud.cafe.post;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	PostRepository postRepository;

	@DisplayName("게시글 작성 페이지 열람")
	@Test
	void showPostPage() throws Exception {
		mockMvc.perform(get("/post"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("postForm"));
	}

	@DisplayName("게시글 추가 - 성공")
	@Test
	void addPostSuccess() throws Exception {
		String testTitle = "testTitle";
		mockMvc.perform(post("/post")
				.param("nickname", "jack")
				.param("title", testTitle)
				.param("textContent", "testContent"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("post"))
			.andExpect(view().name("/post/postDetail"));

		assertThat(postRepository.findByTitle(testTitle)).isPresent();
	}

	@DisplayName("게시글 추가 - 실패")
	@ParameterizedTest
	@CsvSource({"j,testTitle,testContent", "jack,t,textContent", "jack,title,te"})
	void addPostFailureFailed(String nickname, String title, String textContent) throws Exception {
		String testTitle = "testTitle";
		mockMvc.perform(post("/post")
				.param("nickname", nickname)
				.param("title", title)
				.param("textContent", textContent))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("/post/form"));

		assertThat(postRepository.findByTitle(testTitle)).isEmpty();
	}

	@DisplayName("지정 게시글 열람 - 성공")
	@Test
	void testShowPostPageSuccess() throws Exception {
		String testTitle = "testTitle";
		mockMvc.perform(post("/post")
			.param("nickname", "jack")
			.param("title", testTitle)
			.param("textContent", "testContent"));

		Optional<Post> postOptional = postRepository.findByTitle(testTitle);
		Long testId = postOptional.get().getId();

		mockMvc.perform(get("/post/" + testId))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("post"));
	}

	@DisplayName("지정 게시글 열람 - 실패")
	@Test
	void testShowPostPageFailed() throws Exception {
		mockMvc.perform(get("/post/20"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));
	}

	@AfterEach
	void clearRepository() {
		postRepository.clear();
	}
}
