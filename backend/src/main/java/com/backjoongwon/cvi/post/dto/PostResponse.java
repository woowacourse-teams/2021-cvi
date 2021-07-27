package com.backjoongwon.cvi.post.dto;

import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.dto.UserResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PostResponse {

    private Long id;
    private UserResponse writer;
    private String content;
    private int viewCount;
    private int likeCount;
    private boolean hasLiked;
    private VaccinationType vaccinationType;
    private LocalDateTime createdAt;

    public PostResponse(Long id, UserResponse user, String content, int viewCount, int likeCount,
                        boolean hasLiked, VaccinationType vaccinationType, LocalDateTime createdAt) {
        this.id = id;
        this.writer = user;
        this.content = content;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.hasLiked = hasLiked;
        this.vaccinationType = vaccinationType;
        this.createdAt = createdAt;
    }

    public static PostResponse of(Post post, User viewer) {
        return new PostResponse(post.getId(), UserResponse.of(post.getUser(), null), post.getContent(),
                post.getViewCount(), post.getLikesCount(), post.hasLiked(viewer), post.getVaccinationType(), post.getCreatedAt());
    }

    public static List<PostResponse> of(List<Post> posts, User viewer) {
        return posts.stream()
                .map(post -> PostResponse.of(post, viewer))
                .collect(Collectors.toList());
    }
}
