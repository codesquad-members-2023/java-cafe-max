package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.article.ArticleForm;

import java.time.LocalDateTime;

public class Article {
    private Long id; // 글 번호
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private Long points;

    public Article(){}
    public Article(ArticleForm form){
        this.writer = form.getWriter();
        this.title = form.getTitle();
        this.contents = form.getContents();
        this.createdAt = LocalDateTime.now();
        this.points = 1L;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
