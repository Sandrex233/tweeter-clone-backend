package com.auth.authentication.dtos;

public class UserDto {
    private Long id;
    private String email;
    private String token;

    public UserDto() {
        super();
    }

    public UserDto(Long id, String email, String token) {
        super();
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
