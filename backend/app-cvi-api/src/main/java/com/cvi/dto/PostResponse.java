package com.cvi.dto;

import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.VaccinationType;
import com.cvi.user.domain.model.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

    private Long id;
    private UserResponse writer;
    private String content;
    private int viewCount;
    private int likeCount;
    private int commentCount;
    private boolean hasLiked;
    private VaccinationType vaccinationType;
    private LocalDateTime createdAt;
    private List<String> images;

    @Builder
    public PostResponse(Long id, UserResponse writer, String content, int viewCount, int likeCount, int commentCount, boolean hasLiked, VaccinationType vaccinationType, LocalDateTime createdAt, List<String> imageUrls) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.hasLiked = hasLiked;
        this.vaccinationType = vaccinationType;
        this.createdAt = createdAt;
        this.images = imageUrls;
    }

    public static PostResponse of(Post post, User viewer) {
        return new PostResponse(post.getId(), UserResponse.of(post.getUser(), null), post.getContent(),
            post.getViewCount(), post.getLikesCount(), post.getCommentsAsList().size(), post.isAlreadyLikedBy(viewer), post.getVaccinationType(), post.getCreatedAt(), post.getImagesAsUrlList());
    }

    public static PostResponse of(Post post, User viewer, List<String> imageUrls) {
        return new PostResponse(post.getId(), UserResponse.of(post.getUser(), null), post.getContent(),
            post.getViewCount(), post.getLikesCount(), post.getCommentsAsList().size(), post.isAlreadyLikedBy(viewer), post.getVaccinationType(), post.getCreatedAt(), imageUrls);
    }

    public static List<PostResponse> toList(List<Post> posts, User viewer) {
        return posts.stream()
            .map(post -> PostResponse.of(post, viewer))
            .collect(Collectors.toList());
    }
}
