package com.auth.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;


@Entity
@Table(name="user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name = "user_profilePic", length = 1000)
    @Nullable
    private byte[] profilePic;

    @Lob
    @Column(name = "user_coverPic", length = 1000)
    @Nullable
    private byte[] coverPic;
    @Column(name = "user_name")
    @Nullable
    private String name;
    @Column(name = "user_bio")
    @Nullable
    private String bio;
    @Column(name = "user_number")
    @Nullable
    private Long number;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private ApplicationUser user;

    public UserInfo() {
        super();
    }

    public UserInfo(Long id, byte[] profilePic, byte[] coverPic, String name, String bio, Long number, ApplicationUser user) {
        super();
        this.id = id;
        this.profilePic = profilePic;
        this.coverPic = coverPic;
        this.name = name;
        this.bio = bio;
        this.number = number;
        this.user = user;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public byte[] getProfilePic() {
        return profilePic;
    }
    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }
    public byte[] getCoverPic() {
        return coverPic;
    }
    public void setCoverPic(byte[] coverPic) {
        this.coverPic = coverPic;
    }
    public String getName() {
        return name;
    }
    public String getBio() {
        return bio;
    }
    public Long getNumber() {
        return number;
    }
    public ApplicationUser getUser() {
        return user;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setNumber(Long number) {
        this.number = number;
    }
    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}
