package com.cvi.user.domain.model;

import com.cvi.config.entity.BaseEntity;
import com.cvi.exception.InvalidInputException;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@ToString(of = {"nickname", "ageRange", "shotVerified", "socialProvider"})
public class User extends BaseEntity {

    @Column(unique = true)
    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    @Length(max = 20, message = "닉네임의 길이는 최소 1자부터 20자까지 입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "닉네임은 특수문자가 포함될 수 없습니다.")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "연령대는 null일 수 없습니다.")
    private AgeRange ageRange;
    private boolean shotVerified;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "provider(OAuth 제공자)는 null일 수 없습니다.")
    private SocialProvider socialProvider;

    @NotBlank(message = "socialId(OAuth 고유의 id)는 null일 수 없습니다.")
    private String socialId;

    @NotBlank(message = "profileUrl(OAuth profile url)은 null일 수 없습니다.")
    private String profileUrl;

    @Builder
    public User(Long id, LocalDateTime createdAt, LocalDateTime lastModifiedAt, String nickname,
        AgeRange ageRange, boolean shotVerified, SocialProvider socialProvider, String socialId, String profileUrl) {
        super(id, createdAt, lastModifiedAt);
        validateNickName(nickname);
        this.nickname = nickname;
        this.ageRange = ageRange;
        this.shotVerified = shotVerified;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
        this.profileUrl = profileUrl;
    }

    private void validateNickName(String nickname) {
        if (StringUtils.isEmpty(nickname) || nickname.contains(" ")) {
            log.info("닉네임에는 공백 문자가 포함될 수 없습니다. 입력값: {}", nickname);
            throw new InvalidInputException(String.format("닉네임에는 공백 문자가 포함될 수 없습니다. 입력값: %s", nickname));
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
}
