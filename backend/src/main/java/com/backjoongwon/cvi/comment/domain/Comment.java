package com.backjoongwon.cvi.comment.domain;

import com.backjoongwon.cvi.common.domain.entity.BaseEntity;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
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
@AttributeOverride(name = "id", column = @Column(name = "comment_id"))
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private String content;

    @Builder
    public Comment(Long id, LocalDateTime createdAt, LocalDateTime lastModifiedAt, Post post, User user, String content) {
        super(id, createdAt, lastModifiedAt);
        this.post = post;
        this.user = user;
        this.content = content;
    }

    public void assignPost(Post post) {
        if (Objects.isNull(post)) {
            throw new NotFoundException("댓글에 할당하려는 게시글이 없습니다");
        }
        if (Objects.nonNull(this.post)) {
            return;
        }

        this.post = post;
        post.addComment(this);
    }

    public void assignUser(User user) {
        if (Objects.isNull(user)) {
            throw new NotFoundException("댓글을 작성하려는 사용자가 존재하지 않습니다.");
        }
        if (Objects.nonNull(this.user)) {
            throw new InvalidOperationException("한번 할당된 댓글 작성자는 변경할 수 없습니다.");
        }

        this.user = user;
    }

    public void update(Comment updateComment, User user) {
        if (!isSameUser(user)) {
            throw new UnAuthorizedException("다른 사용자의 게시글은 수정할 수 없습니다.");
        }
        this.content = updateComment.content;
    }

    public boolean isSameUser(User user) {
        return this.user.equals(user);
    }

    public boolean isSameAs(Long id) {
        return this.id.equals(id);
    }
}
