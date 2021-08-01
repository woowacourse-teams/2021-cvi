package com.backjoongwon.cvi.post.domain;


import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Likes {

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Like> likes = new ArrayList<>();

    private Like findLikeByUserId(Long userId) {
        return likes.stream()
                .filter(like -> like.isSameUser(userId))
                .findAny()
                .orElseThrow(() -> new NotFoundException("해당 사용자의 좋아요가 글에 존재하지 않습니다."));
    }

    public void delete(Long userId) {
        Like like = findLikeByUserId(userId);
        likes.remove(like);
    }

    public void validateNotExistsLikeCreatedBy(User user) {
        if (isAlreadyLikedBy(user.getId())) {
            throw new InvalidOperationException("해당 게시글에 이미 좋아요를 누른 유저입니다.");
        }
    }

    public boolean isAlreadyLikedBy(Long userId) {
        return likes.stream()
                .anyMatch(like -> like.isSameUser(userId));
    }

    public void add(Like like) {
        if (!likes.contains(like)) {
            likes.add(like);
        }
    }

    public int getSize() {
        return likes.size();
    }
}
