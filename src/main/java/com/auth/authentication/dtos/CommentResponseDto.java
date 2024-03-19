package com.auth.authentication.dtos;

import java.util.Date;

public class CommentResponseDto {
    private Long id;
    private String content;
    private Date dateReplied;
    private Integer authorUserId;
    private String authorName;
    private byte[] authorProfilePic;

    public CommentResponseDto() {
    }

    public CommentResponseDto(Long id, String content, Date dateReplied, Integer authorUserId, String authorName, byte[] authorProfilePic) {
        this.id = id;
        this.content = content;
        this.dateReplied = dateReplied;
        this.authorUserId = authorUserId;
        this.authorName = authorName;
        this.authorProfilePic = authorProfilePic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateReplied() {
        return dateReplied;
    }

    public void setDateReplied(Date dateReplied) {
        this.dateReplied = dateReplied;
    }

    public Integer getAuthorUserId() {
        return authorUserId;
    }

    public void setAuthorUserId(Integer authorUserId) {
        this.authorUserId = authorUserId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public byte[] getAuthorProfilePic() {
        return authorProfilePic;
    }

    public void setAuthorProfilePic(byte[] authorProfilePic) {
        this.authorProfilePic = authorProfilePic;
    }
}
