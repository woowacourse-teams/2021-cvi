package com.backjoongwon.cvi.post.domain;

import com.backjoongwon.cvi.common.exception.InvalidOperationException;
import com.backjoongwon.cvi.common.exception.NotFoundException;
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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private String content;
    private int viewCount;

    @Enumerated(value = EnumType.STRING)
    private VaccinationType vaccinationType;
    private LocalDateTime createdAt;

    @Builder
    public Post(Long id, User user, String content, VaccinationType vaccinationType, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.content = content;
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

    public void increaseViewCount() {
        viewCount++;
    }

    public void update(User user, Post updatePost) {
        if (!this.user.equals(user)) {
            throw new InvalidOperationException("다른 사람의 게시글은 수정할 수 없습니다.");
        }
        this.content = updatePost.content;
    }
}
