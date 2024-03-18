package com.auth.authentication.model;


import jakarta.persistence.*;

@Entity
@Table(name = "user_like")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Like() {
    }

    public Like(Long id, ApplicationUser user, Post post) {
        this.id = id;
        this.user = user;
        this.post = post;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
