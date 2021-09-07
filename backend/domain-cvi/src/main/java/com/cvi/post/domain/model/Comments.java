package com.cvi.post.domain.model;

import com.cvi.comment.domain.model.Comment;
import com.cvi.exception.NotFoundException;
import com.cvi.exception.UnAuthorizedException;
import com.cvi.user.domain.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Comments {

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

    public void assignComment(Comment comment, Post post) {
        if (Objects.isNull(comment)) {
            throw new NotFoundException("댓글이 존재하지 않습니다.");
        }
        comment.assignPost(post);
        if (!comments.contains(comment)) {
            comments.add(comment);
        }
    }

    public void add(Comment comment) {
        comments.add(comment);
    }

    public void update(Long commentId, Comment updateComment, User user) {
        Comment comment = findById(commentId);
        comment.update(updateComment, user);
    }

    public void delete(Long commentId, User user) {
        Comment foundComment = findById(commentId);
        if (!foundComment.isSameUser(user)) {
            throw new UnAuthorizedException("다른 사용자의 게시글은 삭제할 수 없습니다.");
        }
        comments.remove(foundComment);
    }

    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    private Comment findById(Long commentId) {
        return comments.stream()
                .filter(comment -> comment.isSameAs(commentId))
                .findAny()
                .orElseThrow(() -> new NotFoundException("찾을 수 없는 댓글입니다."));
    }
}
