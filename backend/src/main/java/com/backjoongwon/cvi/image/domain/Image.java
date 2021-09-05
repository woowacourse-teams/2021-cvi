package com.backjoongwon.cvi.image.domain;

import com.backjoongwon.cvi.common.domain.entity.BaseEntity;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.post.domain.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "image_id"))
public class Image extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "fk_image_post"))
    private Post post;

    private String url;

    @Builder
    public Image(Long id, LocalDateTime createdAt, LocalDateTime lastModifiedAt, Post post, String url) {
        super(id, createdAt, lastModifiedAt);
        this.post = post;
        this.url = url;
    }

    public void assignPost(Post post) {
        if (Objects.isNull(post)) {
            throw new NotFoundException("이미지에 할당하려는 게시글이 없습니다.");
        }
        if (Objects.nonNull(this.post)) {
            return;
        }
        this.post = post;
        post.assignImages(Collections.singletonList(this));
    }

    public boolean isSameAs(Long id) {
        return this.id.equals(id);
    }

    public String getS3Path() {
        final String[] splitUrl = url.split("/");
        return splitUrl[splitUrl.length - 1];
    }
}