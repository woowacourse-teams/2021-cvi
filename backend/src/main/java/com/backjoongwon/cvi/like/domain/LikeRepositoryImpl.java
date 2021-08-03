package com.backjoongwon.cvi.like.domain;

import com.backjoongwon.cvi.user.domain.QUser;
import com.backjoongwon.cvi.user.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.backjoongwon.cvi.like.domain.QLike.like;

public class LikeRepositoryImpl implements LikeQueryDsl {

    private final JPAQueryFactory queryFactory;

    public LikeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Like> findByUser(User user) {
        return queryFactory.selectFrom(like)
                .leftJoin(like.user, QUser.user).fetchJoin()
                .where(like.user.eq(user))
                .orderBy(like.createdAt.desc())
                .fetch();
    }
}
