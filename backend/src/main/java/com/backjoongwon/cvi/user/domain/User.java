package com.backjoongwon.cvi.user.domain;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private AgeRange ageRange;
    private boolean shotVerified;

    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;
    private String socialProfileUrl;
    private LocalDateTime createdAt;

    @Builder
    public User(Long id, String nickname, AgeRange ageRange, SocialProvider socialProvider,
                String socialProfileUrl, LocalDateTime createdAt) {
        validate(nickname);
        this.id = id;
        this.nickname = nickname;
        this.ageRange = ageRange;
        this.socialProvider = socialProvider;
        this.socialProfileUrl = socialProfileUrl;
        this.createdAt = createdAt;
    }

    private void validate(String nickname) {
        if (StringUtils.isEmpty(nickname) || nickname.contains(" ")) {
            throw new InvalidInputException("닉네임에는 공백 문자가 포함될 수 없습니다.");
        }
    }

    public void update(User updateUser) {
        this.nickname = updateUser.nickname;
        this.ageRange = updateUser.ageRange;
        this.socialProfileUrl = updateUser.socialProfileUrl;
    }

    public void makeVerified() {
        this.shotVerified = true;
    }
}
