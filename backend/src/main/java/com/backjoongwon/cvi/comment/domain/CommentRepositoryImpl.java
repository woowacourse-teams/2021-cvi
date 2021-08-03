package com.backjoongwon.cvi.comment.domain;

import com.backjoongwon.cvi.user.domain.QUser;
import com.backjoongwon.cvi.user.domain.User;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.backjoongwon.cvi.comment.domain.QComment.*;

public class CommentRepositoryImpl implements CommentQueryDsl {

    private final JPQLQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Comment> findByUser(User user) {
        return queryFactory.selectFrom(comment)
                .leftJoin(comment.user, QUser.user).fetchJoin()
                .where(comment.user.eq(user))
                .orderBy(comment.createdAt.desc())
                .fetch();
    }
}
