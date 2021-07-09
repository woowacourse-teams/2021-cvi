package com.backjoongwon.cvi.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        this.id = id;
        this.nickname = nickname;
        this.ageRange = ageRange;
        this.socialProvider = socialProvider;
        this.socialProfileUrl = socialProfileUrl;
        this.createdAt = createdAt;
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
