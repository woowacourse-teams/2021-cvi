package com.cvi.user.domain.model;

import com.cvi.config.entity.BaseEntity;
import com.cvi.exception.InvalidInputException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@ToString(of = {"nickname", "ageRange", "shotVerified", "socialProvider"})
public class User extends BaseEntity {

    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ]{1,20}$");

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private AgeRange ageRange;
    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;
    private boolean shotVerified;
    private String socialId;
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
        if (Objects.isNull(nickname) || !PATTERN.matcher(nickname).matches()) {
            log.info("올바른 닉네임 형식이 아닙니다(특수 문자, 공백 불가). 입력값: {}", nickname);
            throw new InvalidInputException(String.format("올바른 닉네임 형식이 아닙니다(특수 문자, 공백 불가). 입력값: %s", nickname));
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
