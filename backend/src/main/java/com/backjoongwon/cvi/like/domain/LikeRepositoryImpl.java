package com.backjoongwon.cvi.like.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

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
                .where(like.user.id.eq(userId))
                .orderBy(like.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Like> findByUserId(Long userId, Long lastLikeId, int size) {
        return queryFactory.selectFrom(like)
                .leftJoin(like.user, user).fetchJoin()
                .where(like.user.id.eq(userId), like.id.lt(lastLikeId))
                .limit(size)
                .orderBy(like.createdAt.desc())
                .fetch();
    }
}
