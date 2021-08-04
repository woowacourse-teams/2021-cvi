package com.backjoongwon.cvi.user.domain;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import com.backjoongwon.cvi.common.domain.entity.BaseEntity;
import com.backjoongwon.cvi.common.exception.InvalidInputException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseEntity {

    public static final GuestUser GUEST_USER = new GuestUser();

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private AgeRange ageRange;
    private boolean shotVerified;

    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;
    private String socialId;
    private String profileUrl;

    @Builder
    public User(Long id, LocalDateTime createdAt, String nickname, AgeRange ageRange,
                boolean shotVerified, SocialProvider socialProvider, String socialId, String profileUrl) {
        super(id, createdAt);
        this.nickname = nickname;
        validateNickName(nickname);
        this.ageRange = ageRange;
        this.shotVerified = shotVerified;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
        this.profileUrl = profileUrl;
    }

    private void validateNickName(String nickname) {
        if (StringUtils.isEmpty(nickname) || nickname.contains(" ")) {
            throw new InvalidInputException("닉네임에는 공백 문자가 포함될 수 없습니다.");
        }
    }

    public void update(User updateUser) {
        this.nickname = updateUser.nickname;
        this.ageRange = updateUser.ageRange;
        if (updateUser.shotVerified) {
            this.shotVerified = true;
        }
        this.profileUrl = updateUser.profileUrl;
    }

    public void makeVerified() {
        this.shotVerified = true;
    }

    public boolean isGuestUser() {
        return false;
    }

    private static class GuestUser extends User {
        @Override
        public boolean isGuestUser() {
            return true;
        }
    }
}
