package kr.codesqaud.cafe.board.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BoardPost {
    private Long postId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeDt = LocalDateTime.now();

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
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

    public String getWriteDt() {
        return writeDt.format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss"));
    }

    public void setWriteDt(LocalDateTime writeDt) {
        this.writeDt = writeDt;
    }
}
