package com.backjoongwon.cvi.auth.domain.profile;

import lombok.Getter;

@Getter
public class KakaoProfile implements SocialProfile {

    public String id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getNickname() {
        return getNickname();
    }

    @Override
    public String getProfileImage() {
        return this.kakao_account.profile.profile_image_url;
    }

    static class Properties {

        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }

    static class KakaoAccount {

        public Boolean profile_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
        public Boolean has_age_range;
        public Boolean age_range_needs_agreement;
        public Boolean has_birthday;
        public Boolean birthday_needs_agreement;
        public Boolean has_gender;
        public Boolean gender_needs_agreement;
        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
    }

    static class Profile {

        public String nickname;
        public String thumbnail_image_url;
        public String profile_image_url;
        public String is_default_image;
    }
}
