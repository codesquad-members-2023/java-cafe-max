package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.request.PostRequest;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public Article(Long id, String writer, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
    private Article(String writer, String title, String content, LocalDateTime createdAt) {
        this(null, writer, title, content, createdAt);
    }

    public static Article from(final PostRequest postRequest) {
        return new Article(postRequest.getWriter(), postRequest.getTitle(), postRequest.getContent(), LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
