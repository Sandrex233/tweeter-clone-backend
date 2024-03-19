package com.auth.authentication.dtos;

public class UserInfoDto {
    private Integer userId;
    private String email;
    private char[] password;
    private String name;
    private String bio;
    private Long number;
    private byte[] profilePic;
    private byte[] coverPic;

    public UserInfoDto() {
        super();
    }

    public UserInfoDto(Integer userId, String email, char[] password, String name, String bio, Long number, byte[] profilePic, byte[] coverPic) {
        super();
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.number = number;
        this.profilePic = profilePic;
        this.coverPic = coverPic;
    }

    public UserInfoDto(Integer id, String name, byte[] profilePic) {
        this.userId = id;
        this.name = name;
        this.profilePic = profilePic;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public char[] getPassword() {
        return password;
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




    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(char[] password) {
        this.password = password;
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
}