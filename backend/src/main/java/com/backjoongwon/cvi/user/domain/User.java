package com.backjoongwon.cvi.user.domain;

import com.backjoongwon.cvi.common.domain.entity.BaseEntity;
import com.backjoongwon.cvi.common.exception.InvalidInputException;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
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

    private String nickname;

    @Enumerated(EnumType.STRING)
    private AgeRange ageRange;
    private boolean shotVerified;

    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;
    private String socialProfileUrl;

    @Builder
    public User(Long id, String nickname, AgeRange ageRange, SocialProvider socialProvider,
                String socialProfileUrl, LocalDateTime createdAt) {
        super(id, createdAt);
        validateNickName(nickname);
        this.nickname = nickname;
        this.ageRange = ageRange;
        this.socialProvider = socialProvider;
        this.socialProfileUrl = socialProfileUrl;
    }

    private void validateNickName(String nickname) {
        if (StringUtils.isEmpty(nickname) || nickname.contains(" ")) {
            throw new InvalidInputException("닉네임에는 공백 문자가 포함될 수 없습니다.");
        }
    }

    public void update(User updateUser) {
        if (!this.equals(updateUser)) {
            throw new InvalidOperationException("다른 사용자의 정보는 수정할 수 없습니다.");
        }
        this.ageRange = updateUser.ageRange;
    }

    public void makeVerified() {
        this.shotVerified = true;
    }
}
