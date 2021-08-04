package com.backjoongwon.cvi.comment.domain;

import com.backjoongwon.cvi.user.domain.QUser;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.backjoongwon.cvi.comment.domain.QComment.comment;

public class CommentRepositoryImpl implements CommentQueryDsl {

    private final JPQLQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Comment> findByUser(Long id) {
        return queryFactory.selectFrom(comment)
                .leftJoin(comment.user, QUser.user).fetchJoin()
                .where(comment.user.id.eq(id))
                .orderBy(comment.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Comment> findByUser(Long id, Long lastCommentId, int size) {
        return queryFactory.selectFrom(comment)
                .leftJoin(comment.user, QUser.user).fetchJoin()
                .where(comment.user.id.eq(id), comment.id.lt(lastCommentId))
                .limit(size)
                .orderBy(comment.createdAt.desc())
                .fetch();
    }
}
