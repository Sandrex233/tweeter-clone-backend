package com.auth.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String textContent;

    @Lob
    @Column(name = "mediaContent", length = 10000000, nullable = true)
    private byte[] mediaContent;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private ApplicationUser author;

    public Post() {
    }

    public Post(Long id, String textContent, byte[] mediaContent, Date postDate, ApplicationUser author) {
        this.id = id;
        this.textContent = textContent;
        this.mediaContent = mediaContent;
        this.postDate = postDate;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public byte[] getMediaContent() {
        return mediaContent;
    }

    public void setMediaContent(byte[] mediaContent) {
        this.mediaContent = mediaContent;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public ApplicationUser getAuthor() {
        return author;
    }

    public void setAuthor(ApplicationUser author) {
        this.author = author;
    }
}
