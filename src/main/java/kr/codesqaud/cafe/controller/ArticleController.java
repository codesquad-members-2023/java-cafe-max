package kr.codesqaud.cafe.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.codesqaud.cafe.common.resolver.Login;
import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.controller.dto.req.ArticleEditRequest;
import kr.codesqaud.cafe.controller.dto.req.PostingRequest;
import kr.codesqaud.cafe.service.ArticleService;

@RequestMapping("/articles")
@Controller
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping
	public String posting(@ModelAttribute final PostingRequest request, @Login final String userId) {
		articleService.posting(
			new ArticleDto(null, userId, request.getTitle(), request.getContents(), LocalDateTime.now()));
		return "redirect:/";
	}

	@GetMapping("/{articleId}")
	public String showArticleDetails(@PathVariable final Long articleId, final Model model) {
		model.addAttribute("article", articleService.findById(articleId));
		return "qna/show";
	}

	@GetMapping("/{articleId}/form")
	public String showProfileEditPage(@PathVariable final Long articleId,
		@Login final String userId, final Model model) {
		articleService.validateHasAuthorization(articleId, userId);
		model.addAttribute("articleId", articleId);
		return "qna/edit_form";
	}

	@PutMapping("/{articleId}")
	public String editArticle(@PathVariable final Long articleId, @ModelAttribute final ArticleEditRequest request) {
		articleService.editArticle(articleId, request);
		return "redirect:/articles/" + articleId;
	}

	@DeleteMapping("/{articleId}")
	public String deleteArticle(@PathVariable final Long articleId, @Login final String userId) {
		articleService.validateHasAuthorization(articleId, userId);
		articleService.deleteArticle(articleId);
		return "redirect:/";
	}
}
