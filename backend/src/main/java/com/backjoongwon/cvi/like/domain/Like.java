package com.backjoongwon.cvi.like.domain;

import com.backjoongwon.cvi.common.domain.entity.BaseEntity;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "likes_id"))
@Table(name = "likes")
public class Like extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_likes_post"))
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_likes_user"))
    private User user;

    @Builder
    public Like(Long id, LocalDateTime createdAt, LocalDateTime lastModifiedAt, Post post, User user) {
        super(id, createdAt, lastModifiedAt);
        this.post = post;
        this.user = user;
    }

    public void assignPost(Post post) {
        if (Objects.isNull(post)) {
            log.info("좋아요를 저장할 게시글이 없습니다. 입력 값: null");
            throw new NotFoundException("좋아요를 저장할 게시글이 없습니다. 입력 값: null");
        }
        if (Objects.nonNull(this.post)) {
            log.info("한번 할당된 게시글은 변경할 수 없습니다. 입력 값: {}", this.post);
            throw new InvalidOperationException(String.format("한번 할당된 게시글은 변경할 수 없습니다. 입력 값: %s", this.post));
        }
        this.post = post;
        post.getLikes().add(this);
    }

    public boolean isSameUser(Long userId) {
        return this.user.getId().equals(userId);
    }
}
