package com.backjoongwon.cvi.auth.dto.profile;

import lombok.Getter;

@Getter
public class NaverProfile implements SocialProfile {

    private String resultcode;
    private String message;
    private NaverAccount response;

    @Override
    public String extractSocialId() {
        return response.id;
    }

    @Override
    public String extractProfileUrl() {
        return response.profile_image;
    }

    private static class NaverAccount {

        public String id;
        public String nickname;
        public String profile_image;
    }
}
