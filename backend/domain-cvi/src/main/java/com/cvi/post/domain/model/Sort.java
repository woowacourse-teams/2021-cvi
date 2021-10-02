package com.cvi.post.domain.model;

import com.cvi.exception.InvalidInputException;
import com.querydsl.core.types.OrderSpecifier;
import lombok.Getter;

import java.util.Arrays;

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

    private final OrderSpecifier<? extends Comparable<?>> sort;

    Sort(OrderSpecifier<? extends Comparable<?>> sort) {
        this.sort = sort;
    }

    public static OrderSpecifier<? extends Comparable<?>> toOrderSpecifier(Sort input) {
        return Arrays.stream(values())
                .filter(sort -> isSameTypeOf(sort, input))
                .findAny()
                .map(sort -> input.getSort())
                .orElseThrow(() -> new InvalidInputException("잘못된 정렬 형식입니다."));
    }

    private static boolean isSameTypeOf(Sort sort, Sort inputSort) {
        return sort == inputSort;
    }
}