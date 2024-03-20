package com.auth.authentication.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "retweets")
public class Retweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "original_post_id", nullable = false)
    private Post originalPost;

    @ManyToOne
    @JoinColumn(name = "retweeter_id", nullable = false)
    private ApplicationUser retweeter;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRetweeted;

    public Retweet() {
    }

    public Retweet(Post originalPost, ApplicationUser retweeter, Date dateRetweeted) {
        this.originalPost = originalPost;
        this.retweeter = retweeter;
        this.dateRetweeted = dateRetweeted;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Post getOriginalPost() {
        return originalPost;
    }

    public void setOriginalPost(Post originalPost) {
        this.originalPost = originalPost;
    }

    public ApplicationUser getRetweeter() {
        return retweeter;
    }

    public void setRetweeter(ApplicationUser retweeter) {
        this.retweeter = retweeter;
    }

    @Override
    public String toString() {
        return "Retweet{" +
                "id=" + id +
                ", originalPost=" + originalPost +
                ", retweeter=" + retweeter +
                '}';
    }

    public Date getDateRetweeted() {
        return dateRetweeted;
    }

    public void setDateRetweeted(Date dateRetweeted) {
        this.dateRetweeted = dateRetweeted;
    }
}
