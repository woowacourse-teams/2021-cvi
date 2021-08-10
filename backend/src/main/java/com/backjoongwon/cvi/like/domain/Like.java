package com.backjoongwon.cvi.like.domain;

import com.backjoongwon.cvi.common.domain.entity.BaseEntity;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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
        if (Objects.nonNull(this.post)) {
            return;
        }
        this.post = post;
        post.getLikes().add(this);
    }

    public boolean isSameUser(Long userId) {
        return this.user.getId().equals(userId);
    }
}
