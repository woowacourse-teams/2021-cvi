package com.backjoongwon.cvi.post.domain;

import com.backjoongwon.cvi.comment.domain.QComment;
import com.backjoongwon.cvi.like.domain.QLike;
import com.backjoongwon.cvi.user.domain.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.backjoongwon.cvi.post.domain.QPost.post;

public class PostQueryDslImpl implements PostQueryDsl {

    private final JPQLQueryFactory queryFactory;

    public PostQueryDslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> findByVaccineType(VaccinationType vaccinationType) {
        return queryFactory.selectFrom(post)
                .leftJoin(post.user, QUser.user).fetchJoin()
                .where(vaccinationTypeEq(vaccinationType))
                .orderBy(post.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Post> findByVaccineType(VaccinationType vaccinationType, Long lastPostId, int size) {
        return queryFactory.selectFrom(post)
                .leftJoin(post.user, QUser.user).fetchJoin()
                .where(vaccinationTypeEq(vaccinationType), post.id.lt(lastPostId))
                .limit(size)
                .orderBy(post.createdAt.desc())
                .fetch();
    }

    private BooleanExpression vaccinationTypeEq(VaccinationType vaccinationType) {
        if (Objects.nonNull(vaccinationType) && !vaccinationType.equals(VaccinationType.ALL)) {
            return post.vaccinationType.eq(vaccinationType);
        }
        return null;
    }

    @Override
    public Optional<Post> findWithLikesById(Long id) {
        Post post = queryFactory.selectFrom(QPost.post)
                .leftJoin(QPost.post.likes.likes, QLike.like).fetchJoin()
                .where(QPost.post.id.eq(id))
                .orderBy(QPost.post.createdAt.desc())
                .fetchOne();

        return Optional.ofNullable(post);
    }

    @Override
    public Optional<Post> findWithCommentsById(Long id) {
        Post post = queryFactory.selectFrom(QPost.post)
                .leftJoin(QPost.post.comments.comments, QComment.comment).fetchJoin()
                .where(QPost.post.id.eq(id))
                .orderBy(QPost.post.createdAt.desc())
                .fetchOne();

        return Optional.ofNullable(post);
    }
}
