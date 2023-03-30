package kr.codesqaud.cafe.board.repository;

import kr.codesqaud.cafe.board.domain.BoardPost;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BoardRepository {
    Map<Long, BoardPost> boardData = new HashMap<>();

    public void add(BoardPost boardPost) {
        boardPost.setPostId((long) (boardData.size() + 1));
        boardData.put(boardPost.getPostId(), boardPost);
    }

    public BoardPost getPost(Long postId) {
        return boardData.get(postId);
    }

    public List<BoardPost> getPostList() {
        return boardData.values().stream().collect(Collectors.toList());
    }
}
