package kr.codesqaud.cafe.dto.post;

import java.time.LocalDateTime;
import kr.codesqaud.cafe.domain.Post;

public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final WriterResponse writer;
    private final LocalDateTime writeDate;
    private final Long views;

    public PostResponse(Long id, String title, String content, WriterResponse writer,
        LocalDateTime writeDate, Long views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.writeDate = writeDate;
        this.views = views;
    }

    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(),
            WriterResponse.from(post.getWriter()), post.getWriteDate(), post.getViews());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public WriterResponse getWriter() {
        return writer;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Long getViews() {
        return views;
    }
}
