package com.auth.authentication.dtos;

import com.auth.authentication.model.ApplicationUser;

public class LoginResponseDTO {
    private ApplicationUser user;
    private String jwt;

    public LoginResponseDTO() {
        super();
    }

    public LoginResponseDTO(ApplicationUser user, String jwt) {
        this.user = user;
        this.jwt = jwt;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}