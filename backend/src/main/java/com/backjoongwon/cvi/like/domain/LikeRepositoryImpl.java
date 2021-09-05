package com.backjoongwon.cvi.like.domain;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

import static com.backjoongwon.cvi.like.domain.QLike.like;
import static com.backjoongwon.cvi.user.domain.QUser.user;

public class LikeRepositoryImpl implements LikeQueryDsl {

    private final JPAQueryFactory queryFactory;

    public LikeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Like> findByUserId(Long userId) {
        return queryFactory.selectFrom(like)
                .leftJoin(like.user, user).fetchJoin()
                .where(userIdEq(userId))
                .orderBy(like.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Like> findByUserId(Long userId, int offset, int size) {
        return queryFactory.selectFrom(like)
                .leftJoin(like.user, user).fetchJoin()
                .where(userIdEq(userId))
                .orderBy(like.createdAt.desc())
                .offset(offset)
                .limit(size)
                .fetch();
    }

    private BooleanExpression userIdEq(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }
        return like.user.id.eq(userId);
    }
}
