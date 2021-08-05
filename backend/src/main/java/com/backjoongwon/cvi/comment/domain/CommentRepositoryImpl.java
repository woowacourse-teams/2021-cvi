package com.backjoongwon.cvi.comment.domain;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.backjoongwon.cvi.comment.domain.QComment.comment;
import static com.backjoongwon.cvi.user.domain.QUser.user;

public class CommentRepositoryImpl implements CommentQueryDsl {

    private final JPQLQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Comment> findByUserId(Long userId) {
        return queryFactory.selectFrom(comment)
                .leftJoin(comment.user, user).fetchJoin()
                .where(comment.user.id.eq(userId))
                .orderBy(comment.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Comment> findByUserId(Long userId, int offset, int size) {
        return queryFactory.selectFrom(comment)
                .leftJoin(comment.user, user).fetchJoin()
                .where(comment.user.id.eq(userId))
                .orderBy(comment.createdAt.desc())
                .offset(offset)
                .limit(size)
                .fetch();
    }
}
