package com.auth.authentication.dtos;

import org.springframework.web.multipart.MultipartFile;

public class PostDto {
    private String textContent;
    private transient MultipartFile mediaContent;

    public PostDto() {
    }

    public PostDto(String textContent, MultipartFile mediaContent) {
        this.textContent = textContent;
        this.mediaContent = mediaContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public MultipartFile getMediaContent() {
        return mediaContent;
    }

    public void setMediaContent(MultipartFile mediaContent) {
        this.mediaContent = mediaContent;
    }
}
