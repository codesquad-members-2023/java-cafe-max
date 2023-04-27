package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private long id;     // 자꾸 헷갈려서 단순하게 변경완료
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdTime;

    public Article(){}   // 없으면 (Row?)Mapping이 안된다는...?!?!
    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdTime = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)); // this.createdTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) { this.writer = writer; }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) { this.contents = contents; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getCreatedTime() { return createdTime; }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
