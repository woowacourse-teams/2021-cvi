package com.backjoongwon.cvi.user.domain;

import com.backjoongwon.cvi.auth.domain.authorization.SocialProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);

    Optional<User> findByNickname(String nickname);

    Optional<User> findBySocialProviderAndSocialId(SocialProvider provider, String socialId);
}
