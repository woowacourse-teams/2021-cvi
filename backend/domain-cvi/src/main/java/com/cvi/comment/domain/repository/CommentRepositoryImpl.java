package com.cvi.comment.domain.repository;

import com.cvi.comment.domain.model.Comment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

import static com.cvi.comment.domain.model.QComment.comment;
import static com.cvi.user.domain.model.QUser.user;

public class CommentRepositoryImpl implements CommentQueryDsl {

    private final JPQLQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Comment> findByUserId(Long userId) {
        return queryFactory.selectFrom(comment)
            .leftJoin(comment.user, user).fetchJoin()
            .where(userIdEq(userId))
            .orderBy(comment.createdAt.desc())
            .fetch();
    }

    @Override
    public List<Comment> findByUserId(Long userId, int offset, int size) {
        return queryFactory.selectFrom(comment)
            .leftJoin(comment.user, user).fetchJoin()
            .where(userIdEq(userId))
            .orderBy(comment.createdAt.desc())
            .offset(offset)
            .limit(size)
            .fetch();
    }

    private BooleanExpression userIdEq(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }
        return comment.user.id.eq(userId);
    }
}
