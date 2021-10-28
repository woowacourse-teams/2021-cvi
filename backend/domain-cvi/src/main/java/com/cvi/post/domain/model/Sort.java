package com.cvi.post.domain.model;

import com.querydsl.core.types.OrderSpecifier;
import lombok.Getter;

import static com.cvi.post.domain.model.QPost.post;

@Getter
public enum Sort {
    LIKE_COUNT_ASC(post.likes.likes.size().asc()),
    LIKE_COUNT_DESC(post.likes.likes.size().desc()),
    VIEW_COUNT_ASC(post.viewCount.asc()),
    VIEW_COUNT_DESC(post.viewCount.desc()),
    COMMENT_COUNT_ASC(post.comments.comments.size().asc()),
    COMMENT_COUNT_DESC(post.comments.comments.size().desc()),
    CREATED_AT_ASC(post.createdAt.asc()),
    CREATED_AT_DESC(post.createdAt.desc());

    private final OrderSpecifier sort;

    Sort(OrderSpecifier sort) {
        this.sort = sort;
    }
}
