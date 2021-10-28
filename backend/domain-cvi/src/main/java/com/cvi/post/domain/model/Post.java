package com.cvi.post.domain.model;

import com.cvi.comment.domain.model.Comment;
import com.cvi.config.entity.BaseEntity;
import com.cvi.exception.InvalidOperationException;
import com.cvi.exception.NotFoundException;
import com.cvi.image.domain.Image;
import com.cvi.like.domain.model.Like;
import com.cvi.user.domain.model.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "post_id"))
@ToString(of = {"content", "viewCount", "vaccinationType"})
public class Post extends BaseEntity {

    @Embedded
    private final Images images = new Images();

    @Embedded
    private final Likes likes = new Likes();

    @Embedded
    private final Comments comments = new Comments();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_post_user"))
    private User user;

    @Column(name = "content", columnDefinition = "text")
    @Lob
    @NotBlank(message = "게시글의 내용은 비어있을 수 없습니다.")
    private String content;
    private int viewCount;

    @Enumerated(value = EnumType.STRING)
    private VaccinationType vaccinationType;

    @Builder
    public Post(Long id, LocalDateTime createdAt, LocalDateTime lastModifiedAt, User user,
                String content, int viewCount, VaccinationType vaccinationType) {
        super(id, createdAt, lastModifiedAt);
        this.user = user;
        this.content = content;
        this.viewCount = viewCount;
        this.vaccinationType = vaccinationType;
        this.createdAt = createdAt;
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

    public void updateContent(Post updatePost, User user) {
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

    public boolean isAlreadyLikedBy(User user) {
        if (Objects.isNull(user)) {
            return false;
        }
        return likes.isAlreadyLikedBy(user.getId());
    }

    public void deleteLike(Long userId) {
        likes.delete(userId);
    }

    public int getLikesCount() {
        return likes.getSize();
    }

    public void assignComment(Comment comment) {
        comments.assignComment(comment, this);
    }

    public void updateComment(Long commentId, Comment updateComment, User user) {
        comments.update(commentId, updateComment, user);
    }

    public void deleteComment(Long commentId, User user) {
        comments.delete(commentId, user);
    }

    public void assignLike(Like like) {
        likes.assignLike(like, this);
    }

    public List<Comment> getCommentsAsList() {
        return comments.getComments();
    }

    public List<Comment> sliceCommentsAsList(int offset, int size) {
        List<Comment> comments = this.comments.getComments();
        comments = comments.stream()
            .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
            .collect(Collectors.toList());
        int fromIndex = offset;
        fromIndex = resizePagingRange(comments, fromIndex);
        int toIndex = offset + size;
        toIndex = resizePagingRange(comments, toIndex);
        return comments.subList(fromIndex, toIndex);
    }

    private int resizePagingRange(List<Comment> comments, int fromIndex) {
        if (fromIndex > comments.size()) {
            fromIndex = comments.size();
        }
        return fromIndex;
    }

    public void assignImages(List<Image> images) {
        this.images.addAll(images, this);
    }

    public List<String> getImagesAsUrlList() {
        return images.getImages().stream()
            .map(Image::getUrl)
            .collect(Collectors.toList());
    }

    public boolean hasImages() {
        return images.size() > 0;
    }

    public List<Image> getAllImagesAsList() {
        return new ArrayList<>(images.getImages());
    }

    public List<String> getS3PathsOfAllImages() {
        return images.getS3PathsOfAllImages();
    }
}
