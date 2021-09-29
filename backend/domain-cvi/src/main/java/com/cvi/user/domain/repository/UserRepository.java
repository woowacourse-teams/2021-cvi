package com.cvi.user.domain.repository;

import com.cvi.user.domain.model.SocialProvider;
import com.cvi.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);

    Optional<User> findBySocialProviderAndSocialId(SocialProvider provider, String socialId);
}
