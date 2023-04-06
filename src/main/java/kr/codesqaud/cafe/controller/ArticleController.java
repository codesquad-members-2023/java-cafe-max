package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ArticleController {
    private final ArticleService articleService;
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/article/write")
    public String postQuestion(ArticleFormDto articleFormDto) {
        articleService.writeArticle(articleFormDto);
        return "redirect:/";
    }

    @GetMapping("/article/show/{index}")
    public String getShow(@PathVariable int index, Model model) {
        Article article = articleService.findByIDX(index);
        model.addAttribute("article", article);
        return "qna/show";
    }


    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("articleList", articleService.getAricleList());
        return "index";
    }

}
