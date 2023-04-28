package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.exception.DeniedCommentModificationException;

public class Comment {
	private Long commentIndex;
	private Long articleIndex;
	private String author;
	private String comment;
	private String createdDate;
	private boolean deleted;
	private String modDate;

	public Comment(Long commentIndex, Long articleIndex, String author, String comment, String createdDate,
		boolean deleted) {
		this.commentIndex = commentIndex;
		this.articleIndex = articleIndex;
		this.author = author;
		this.comment = comment;
		this.createdDate = createdDate;
		this.deleted = deleted;
	}

	public Comment(Long articleIndex, String author, String comment, String createdDate, boolean deleted) {
		this.articleIndex = articleIndex;
		this.author = author;
		this.comment = comment;
		this.createdDate = createdDate;
		this.deleted = deleted;
	}

	public Comment(String comment, String modDate) {
		this.comment = comment;
		this.modDate = modDate;
	}

	public Long getCommentIndex() {
		return commentIndex;
	}

	public Long getArticleIndex() {
		return articleIndex;
	}

	public String getAuthor() {
		return author;
	}

	public String getComment() {
		return comment;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void validateAuthor(String nickname) {
		if (!author.equals(nickname)) {
			throw new DeniedCommentModificationException();
		}
	}

	public String getModDate() {
		return modDate;
	}
}
