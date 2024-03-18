package com.auth.authentication.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_followers", uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "followed_id"}))
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private ApplicationUser follower;
    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private ApplicationUser followed;

    public Follow() {
    }

    public Follow(Long id, ApplicationUser follower, ApplicationUser followed) {
        this.id = id;
        this.follower = follower;
        this.followed = followed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationUser getFollower() {
        return follower;
    }

    public void setFollower(ApplicationUser follower) {
        this.follower = follower;
    }

    public ApplicationUser getFollowed() {
        return followed;
    }

    public void setFollowed(ApplicationUser followed) {
        this.followed = followed;
    }
}
