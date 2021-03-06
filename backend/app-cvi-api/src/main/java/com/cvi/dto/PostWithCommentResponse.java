package com.cvi.dto;

import com.cvi.comment.domain.model.Comment;
import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.user.domain.model.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostWithCommentResponse {

    private Long id;
    private UserResponse writer;
    private String content;
    private int viewCount;
    private int likeCount;
    private boolean hasLiked;
    private List<CommentResponse> comments;
    private VaccinationType vaccinationType;
    private LocalDateTime createdAt;
    private List<String> images;

    public PostWithCommentResponse(Long id, UserResponse user, String content, int viewCount, int likeCount,
        boolean hasLiked, List<CommentResponse> comments, VaccinationType vaccinationType, LocalDateTime createdAt, List<String> imageUrls) {
        this.id = id;
        this.writer = user;
        this.content = content;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.hasLiked = hasLiked;
        this.comments = comments;
        this.vaccinationType = vaccinationType;
        this.createdAt = createdAt;
        this.images = imageUrls;
    }

    public static PostWithCommentResponse of(Post post, User viewer) {
        return new PostWithCommentResponse(post.getId(), UserResponse.of(post.getUser(), null), post.getContent(),
            post.getViewCount(), post.getLikesCount(), post.isAlreadyLikedBy(viewer),
            makeCommentResponses(post.getCommentsAsList()), post.getVaccinationType(), post.getCreatedAt(), post.getImagesAsUrlList());
    }

    private static List<CommentResponse> makeCommentResponses(List<Comment> comments) {
        return comments.stream()
            .map(CommentResponse::of)
            .collect(Collectors.toList());
    }

    public static List<PostWithCommentResponse> toList(List<Post> posts, User viewer) {
        return posts.stream()
            .map(post -> PostWithCommentResponse.of(post, viewer))
            .collect(Collectors.toList());
    }
}
