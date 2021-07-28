package com.backjoongwon.cvi.post.domain;

import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.common.domain.entity.BaseEntity;
import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "post_id"))
public class Post extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private String content;
    private int viewCount;

    @Enumerated(value = EnumType.STRING)
    private VaccinationType vaccinationType;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(Long id, User user, String content, VaccinationType vaccinationType, LocalDateTime createdAt) {
        super(id, createdAt);
        this.user = user;
        this.content = content;
        this.vaccinationType = vaccinationType;
    }

    public void assignUser(User user) {
        if (Objects.isNull(user)) {
            throw new NotFoundException("작성자가 존재하지 않습니다.");
        }
        if (Objects.nonNull(this.user)) {
            throw new InvalidOperationException("작성자는 변경할 수 없습니다.");
        }
        this.user = user;
    }

    public void update(Post updatePost, User user) {
        if (!this.user.equals(user)) {
            throw new InvalidOperationException("다른 사용자의 게시글은 수정할 수 없습니다.");
        }
        this.content = updatePost.content;
    }

    public void increaseViewCount() {
        viewCount++;
    }

    public void validateAuthor(User user) {
        if (!this.user.equals(user)) {
            throw new InvalidOperationException("다른 사용자의 게시글은 삭제할 수 없습니다.");
        }
    }

    public int getLikesCount() {
        return likes.size();
    }

    public boolean hasLikedBy(User viewer) {
        return likes.stream()
                .anyMatch(like -> like.isCreatedBy(viewer));
    }

    public void addLike(Like like) {
        validateDuplicateLikes(like);
         like.assignPost(this);
        if (!likes.contains(like)) {
            likes.add(like);
        }
    }

    private void validateDuplicateLikes(Like newLike) {
        if (hasLikedBySameUser(newLike)) {
            throw new InvalidOperationException("해당 게시글에 이미 좋아요를 누른 유저입니다.");
        }
    }

    private boolean hasLikedBySameUser(Like newLike) {
        return likes.stream()
                .anyMatch(like -> like.isSameUser(newLike.getUser()));
    }
}
