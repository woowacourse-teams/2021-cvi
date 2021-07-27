package com.backjoongwon.cvi.like.domain;

import com.backjoongwon.cvi.common.domain.entity.BaseEntity;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Table(name = "likes")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "likes_id"))
public class Like extends BaseEntity  {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Like(Long id, LocalDateTime createdAt, Post post, User user) {
        super(id, createdAt);
        this.post = post;
        this.user = user;
    }

    public boolean isCreatedBy(User user) {
        return this.user.equals(user);
    }

    public void assignPost(Post post) {
        if (Objects.nonNull(this.post)) {
            return;
        }
        this.post = post;
        post.getLikes().add(this);
    }
}
