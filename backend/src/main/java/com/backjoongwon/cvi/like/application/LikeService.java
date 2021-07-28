package com.backjoongwon.cvi.like.application;

import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.user.domain.RequestUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;

    public void delete(Long id, RequestUser user) {
        user.validateSignedin();
        Like like = likeRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new NotFoundException("유저가 생성한 해당 id의 좋아요가 존재하지 않습니다."));
        likeRepository.delete(like);
    }
}
