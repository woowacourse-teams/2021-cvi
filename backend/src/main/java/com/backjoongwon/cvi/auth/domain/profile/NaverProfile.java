package com.backjoongwon.cvi.auth.domain.profile;


import lombok.Getter;

@Getter
public class NaverProfile implements SocialProfile {

    private String resultcode;
    private String message;
    private Response response;

    @Override
    public String getId() {
        return response.id;
    }

    @Override
    public String getNickname() {
        return response.nickname;
    }

    @Override
    public String getProfileImage() {
        return response.profile_image;
    }

    static class Response {

        public String id;
        public String nickname;
        public String profile_image;
    }
}
