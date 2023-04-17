package kr.codesqaud.cafe.service;

import static kr.codesqaud.cafe.fixture.FixtureFactory.createArticle;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kr.codesqaud.cafe.controller.dto.req.ArticleEditRequest;
import kr.codesqaud.cafe.controller.dto.req.PostingRequest;
import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.exception.NoAuthorizationException;
import kr.codesqaud.cafe.exception.NotFoundException;
import kr.codesqaud.cafe.repository.ArticleCommentRepository;
import kr.codesqaud.cafe.repository.ArticleRepository;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

	@Mock
	private ArticleRepository articleRepository;

	@Mock
	private ArticleCommentRepository articleCommentRepository;

	@InjectMocks
	private ArticleService articleService;

	@DisplayName("게시글을 올릴 때")
	@Nested
	class PostingTest {

		@DisplayName("포스팅 정보가 주어지면 포스팅에 성공한다.")
		@Test
		void givenPostingRequest_whenPosting_thenReturnsNothing() {
			// given
			PostingRequest request = new PostingRequest("게시글 제목", "게시글 내용");
			given(articleRepository.save(any(Article.class))).willReturn(Optional.of(createArticle()));

			// when & then
			assertAll(
				() -> assertThatCode(() -> articleService.post(request, "브루니"))
					.doesNotThrowAnyException(),
				() -> then(articleRepository).should().save(any(Article.class))
			);
		}
	}

	@DisplayName("게시글을 조회할 때")
	@Nested
	class ArticleFindTest {

		@DisplayName("모든 게시글을 조회하면 게시글 리스트를 반환한다.")
		@Test
		void givenNothing_whenFindAll_thenReturnsArticleList() {
			// given
			given(articleRepository.findAll())
				.willReturn(List.of(createArticle(), createArticle(), createArticle()));

			// when & then
			assertAll(
				() -> assertThatCode(() -> articleService.getArticles())
					.doesNotThrowAnyException(),
				() -> then(articleRepository).should().findAll()
			);
		}
	}

	@DisplayName("게시글의 권한을 검증할 때")
	@Nested
	class ValidateHasAuthorizationTest {

		@DisplayName("게시글의 작성자와 로그인한 사용자가 일치하면 검증에 성공한다.")
		@Test
		void givenSameArticleWriterAndUserId_whenValidateHasAuthorization_thenDoNothing() {
			// given
			given(articleRepository.findById(1L)).willReturn(Optional.of(createArticle()));
			String userId = "bruni";

			// when & then
			assertAll(
				() -> assertThatCode(() -> articleService.validateHasAuthorization(1L, userId))
					.doesNotThrowAnyException(),
				() -> then(articleRepository).should().findById(1L)
			);
		}

		@DisplayName("게시글의 작성자와 로그인한 사용자가 일치하지 않으면 예외를 던진다.")
		@Test
		void givenNotEqualArticleWriterAndUserId_whenValidateHasAuthorization_throwsException() {
			// given
			given(articleRepository.findById(anyLong())).willReturn(Optional.of(createArticle()));
			String userId = "unknown";

			// when & then
			assertAll(
				() -> assertThatThrownBy(() -> articleService.validateHasAuthorization(1L, userId))
					.isInstanceOf(NoAuthorizationException.class),
				() -> then(articleRepository).should().findById(1L)
			);
		}
	}

	@DisplayName("게시글을 수정할 때")
	@Nested
	class EditArticleTest {

		@DisplayName("게시글 수정정보가 주어지면 게시글을 수정한다.")
		@Test
		void givenArticleEditInfo_whenEditsArticle_thenDoNothing() {
			// given
			given(articleRepository.findById(anyLong())).willReturn(Optional.of(createArticle()));
			willDoNothing().given(articleRepository).update(any(Article.class));
			ArticleEditRequest request = new ArticleEditRequest("수정된 제목", "수정된 내용");

			// when
			articleService.editArticle(1L, request);

			// then
			assertAll(
				() -> then(articleRepository).should().findById(1L),
				() -> then(articleRepository).should().update(any(Article.class))
			);
		}

		@DisplayName("존재하지 않는 게시글 아이디가 주어지면 예외를 던진다.")
		@Test
		void givenNotExistsArticleId_whenEditsArticle_thenThrowsException() {
			// given
			given(articleRepository.findById(anyLong())).willReturn(Optional.empty());
			ArticleEditRequest request = new ArticleEditRequest("수정된 제목", "수정된 내용");

			// when & then
			assertAll(
				() -> assertThatThrownBy(() -> articleService.editArticle(1L, request))
					.isInstanceOf(NotFoundException.class),
				() -> then(articleRepository).should().findById(1L),
				() -> then(articleRepository).should(never()).update(any(Article.class))
			);
		}
	}

	@DisplayName("게시글을 삭제할 때")
	@Nested
	class DeleteArticleTest {

		@DisplayName("삭제에 성공한다.")
		@Test
		void givenNothing_whenDeletesArticle_thenDoNothing() {
			// given
			given(articleRepository.findById(anyLong())).willReturn(Optional.of(createArticle()));
			willDoNothing().given(articleRepository).deleteById(anyLong());

			// when & then
			assertAll(
				() -> assertThatCode(() -> articleService.deleteArticle(1L))
					.doesNotThrowAnyException(),
				() -> then(articleRepository).should().findById(1L),
				() -> then(articleRepository).should().deleteById(1L)
			);
		}

		@DisplayName("존재하지 않는 게시글 아이디가 주어지면 예외를 던진다.")
		@Test
		void givenNotExistsArticleId_whenDeletesArticle_thenThrowsException() {
			// given
			given(articleRepository.findById(anyLong())).willReturn(Optional.empty());

			// when & then
			assertAll(
				() -> assertThatThrownBy(() -> articleService.deleteArticle(1L))
					.isInstanceOf(NotFoundException.class),
				() -> then(articleRepository).should().findById(1L),
				() -> then(articleRepository).should(never()).deleteById(1L)
			);
		}
	}
}
