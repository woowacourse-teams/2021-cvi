package com.backjoongwon.cvi.post.domain;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.backjoongwon.cvi.comment.domain.QComment.comment;
import static com.backjoongwon.cvi.like.domain.QLike.like;
import static com.backjoongwon.cvi.post.domain.QPost.post;
import static com.backjoongwon.cvi.user.domain.QUser.user;

public class PostRepositoryImpl implements PostQueryDsl {

    private final JPQLQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> findByVaccineType(VaccinationType vaccinationType) {
        return queryFactory.selectFrom(post)
                .leftJoin(post.user, user).fetchJoin()
                .where(vaccinationTypeEq(vaccinationType))
                .orderBy(post.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Post> findByVaccineType(VaccinationType vaccinationType, Long lastPostId, int size, OrderSpecifier orderSpecifier, int hours) {
        return queryFactory.selectFrom(post)
                .leftJoin(post.user, user).fetchJoin()
                .where(vaccinationTypeEq(vaccinationType), lessThan(lastPostId), fromHoursBefore(hours))
                .limit(size)
                .orderBy(orderSpecifier, post.createdAt.desc())
                .fetch();
    }

    private BooleanExpression lessThan(Long lastPostId) {
        return post.id.lt(lastPostId);
    }

    private BooleanExpression fromHoursBefore(int hours) {
        return post.createdAt.after(LocalDateTime.now().minusHours(hours));
    }

    private BooleanExpression vaccinationTypeEq(VaccinationType vaccinationType) {
        if (Objects.nonNull(vaccinationType) && !vaccinationType.equals(VaccinationType.ALL)) {
            return post.vaccinationType.eq(vaccinationType);
        }
        return null;
    }

    @Override
    public List<Post> findByUserId(Long userId) {
        return queryFactory.selectFrom(post)
                .leftJoin(post.user, user).fetchJoin()
                .where(post.user.id.eq(userId))
                .orderBy(post.createdAt.desc())
                .fetch();
    }

    @Override
    public Optional<Post> findWithLikesById(Long id) {
        Post post = queryFactory.selectFrom(QPost.post)
                .leftJoin(QPost.post.likes.likes, like).fetchJoin()
                .where(QPost.post.id.eq(id))
                .orderBy(QPost.post.createdAt.desc())
                .fetchOne();

        return Optional.ofNullable(post);
    }

    @Override
    public Optional<Post> findWithCommentsById(Long id) {
        Post post = queryFactory.selectFrom(QPost.post)
                .leftJoin(QPost.post.comments.comments, comment).fetchJoin()
                .where(QPost.post.id.eq(id))
                .orderBy(QPost.post.createdAt.desc())
                .fetchOne();

        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findByUserId(Long userId, Long lastPostId, int size) {
        return queryFactory.selectFrom(post)
                .leftJoin(post.user, user).fetchJoin()
                .where(post.user.id.eq(userId), lessThan(lastPostId))
                .limit(size)
                .orderBy(post.createdAt.desc())
                .fetch();
    }
}
