package com.backjoongwon.cvi.user.dto;

public class UserResponse {

    private Long id;
    private String nickname;
    private int ageRange;

    protected UserResponse() {
    }

    public UserResponse(Long id, String nickname, int ageRange) {
        this.id = id;
        this.nickname = nickname;
        this.ageRange = ageRange;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAgeRange() {
        return ageRange;
    }
}
