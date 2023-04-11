package kr.codesqaud.cafe.domain.article.repository;

import kr.codesqaud.cafe.domain.article.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);

    List<Article> findAll();

    Article findByIdx(int idx);

    void update(Article article);

    void delete(int index);
}
