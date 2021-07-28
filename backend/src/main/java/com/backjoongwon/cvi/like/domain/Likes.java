package com.backjoongwon.cvi.like.domain;


import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
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
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class Likes {

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    private Like findLikeById(Long id) {
        return likes.stream()
                .filter(like -> like.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new NotFoundException("해당 id의 좋아요가 존재하지 않습니다."));
    }

    public void remove(Long likeId, Long userId) {
        Like like = findLikeById(likeId);
        like.validateOwner(userId);
        likes.remove(like);
    }

    public void validateNotExistsLikeCreatedBy(User user) {
        if (hasCreatedBy(user.getId())) {
            throw new InvalidOperationException("해당 게시글에 이미 좋아요를 누른 유저입니다.");
        }
    }

    public boolean hasCreatedBy(Long userId) {
        return likes.stream()
                .anyMatch(like -> like.isCreatedBy(userId));
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
