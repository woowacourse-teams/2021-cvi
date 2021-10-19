package com.cvi.comment.domain.model;

import com.cvi.config.entity.BaseEntity;
import com.cvi.exception.InvalidOperationException;
import com.cvi.exception.NotFoundException;
import com.cvi.exception.UnAuthorizedException;
import com.cvi.post.domain.model.Post;
import com.cvi.user.domain.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "comment_id"))
public class Comment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_comment_post"))
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_comment_user"))
    private User user;

    @Lob
    @Column(name = "content", columnDefinition = "text")
    @Length(max = 300)
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
            log.info("댓글을 저장할 게시글이 없습니다. 입력 값: null");
            throw new NotFoundException("댓글을 저장할 게시글이 없습니다 입력 값: null");
        }
        if (Objects.nonNull(this.post)) {
            log.info("한번 할당된 게시글은 변경할 수 없습니다. 입력 값: {}", this.post);
            throw new InvalidOperationException(String.format("한번 할당된 게시글은 변경할 수 없습니다. 입력 값: %s", this.post));
        }
        this.post = post;
        post.getComments().add(this);
    }

    public void assignUser(User user) {
        if (Objects.isNull(user)) {
            log.info("댓글을 작성하려는 사용자가 존재하지 않습니다.입력 값: null");
            throw new NotFoundException("댓글을 작성하려는 사용자가 존재하지 않습니다.입력 값: null");
        }
        if (Objects.nonNull(this.user)) {
            log.info("한번 할당된 댓글 작성자는 변경할 수 없습니다. 입력 값: {}", this.user);
            throw new InvalidOperationException(String.format("한번 할당된 댓글 작성자는 변경할 수 없습니다. 입력 값: %s", this.user));
        }
        this.user = user;
    }

    public void update(Comment updateComment, User user) {
        if (!isSameUser(user)) {
            log.info("다른 사용자의 댓글은 수정할 수 없습니다. 입력 값: {}", this.user);
            throw new UnAuthorizedException(String.format("다른 사용자의 댓글은 수정할 수 없습니다.입력 값: %s", this.user));
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
