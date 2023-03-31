package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.dto.ArticleForm;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/qna/form")
    public String write(ArticleForm form) {

        Article article = new Article(form.getWriter(), form.getTitle(), form.getContents());
        articleService.write(article);

        return "redirect:/";
    }

    //todo : "/"을 담당하는 컨트롤러에서 이 기능 구현?
    @GetMapping(value = {"/", "/index"})
    public String list(Model model) {
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "/index";
    }
}
