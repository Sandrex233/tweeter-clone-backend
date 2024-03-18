package com.auth.authentication.dtos;

import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

public class EditDto {
    @Nullable
    private transient MultipartFile profilePhoto;
    @Nullable
    private transient MultipartFile coverPhoto;
    @Nullable
    private String name;
    @Nullable
    private String bio;
    @Nullable
    private Long number;

    public EditDto() {
        super();
    }

    public EditDto(MultipartFile profilePhoto, MultipartFile coverPhoto, String name, String bio, Long number) {
        super();
        this.profilePhoto = profilePhoto;
        this.coverPhoto = coverPhoto;
        this.name = name;
        this.bio = bio;
        this.number = number;
    }

    public MultipartFile getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(MultipartFile profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public MultipartFile getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(MultipartFile coverPhoto) {
        this.coverPhoto = coverPhoto;
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



    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
