package com.backjoongwon.cvi.user.dto;

public class UserRequest {

    private String nickname;
    private int ageRange;

    protected UserRequest() {
    }

    public UserRequest(String nickname, int ageRange) {
        this.nickname = nickname;
        this.ageRange = ageRange;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAgeRange() {
        return ageRange;
    }
}
