package com.cvi.post.domain.model;

import com.cvi.exception.InvalidInputException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Getter;

import java.util.function.BiFunction;

import static com.cvi.post.domain.model.QPost.post;

@Getter
public enum Sort {
    LIKE_COUNT_ASC(post.likes.likes.size().asc(),
            (boundary, id) -> post.likes.likes.size().eq(boundary).and(sortByCreatedAtDescending(id)).or(post.likes.likes.size().gt(boundary))),
    LIKE_COUNT_DESC(post.likes.likes.size().desc(),
            (boundary, id) -> post.likes.likes.size().eq(boundary).and(sortByCreatedAtDescending(id)).or(post.likes.likes.size().lt(boundary))),
    VIEW_COUNT_ASC(post.viewCount.asc(),
            (boundary, id) -> post.viewCount.eq(boundary).and(sortByCreatedAtDescending(id)).or(post.viewCount.gt(boundary))),
    VIEW_COUNT_DESC(post.viewCount.desc(),
            (boundary, id) -> post.viewCount.eq(boundary).and(sortByCreatedAtDescending(id)).or(post.viewCount.lt(boundary))),
    COMMENT_COUNT_ASC(post.comments.comments.size().asc(),
            (boundary, id) -> post.comments.comments.size().eq(boundary).and(sortByCreatedAtDescending(id)).or(post.comments.comments.size().gt(boundary))),
    COMMENT_COUNT_DESC(post.comments.comments.size().desc(),
            (boundary, id) -> post.comments.comments.size().eq(boundary).and(sortByCreatedAtDescending(id)).or(post.comments.comments.size().lt(boundary))),
    CREATED_AT_ASC(post.id.asc(),
            (boundary, id) -> sortByCreatedAtAscending(id)),
    CREATED_AT_DESC(post.id.desc(),
            (boundary, id) -> sortByCreatedAtDescending(id));

    private final OrderSpecifier<? extends Comparable<?>> sort;
    private final BiFunction<Integer, Long, BooleanExpression> boundary;

    Sort(OrderSpecifier<? extends Comparable<?>> sort, BiFunction<Integer, Long, BooleanExpression> boundary) {
        this.sort = sort;
        this.boundary = boundary;
    }

    public static OrderSpecifier<? extends Comparable<?>> toOrderSpecifier(Sort inputSort) {
        if (inputSort.getSort() != null) {
            return inputSort.getSort();
        }
        throw new InvalidInputException("잘못된 정렬 형식입니다");
    }

    public static BooleanExpression toBooleanExpression(int boundary, long id, Sort inputSort) {
        return inputSort.boundary.apply(boundary, id);
    }

    private static BooleanExpression sortByCreatedAtAscending(Long id) {
        return post.id.gt(id);
    }

    private static BooleanExpression sortByCreatedAtDescending(Long id) {
        return post.id.lt(id);
    }
}