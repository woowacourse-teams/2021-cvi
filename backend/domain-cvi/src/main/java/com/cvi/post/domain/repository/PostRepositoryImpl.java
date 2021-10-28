package com.cvi.post.domain.repository;

import com.cvi.post.domain.model.Post;
import com.cvi.post.domain.model.QPost;
import com.cvi.post.domain.model.VaccinationType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.cvi.comment.domain.model.QComment.comment;
import static com.cvi.like.domain.model.QLike.like;
import static com.cvi.post.domain.model.QPost.post;
import static com.cvi.user.domain.model.QUser.user;

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
    public List<Post> findByVaccineType(VaccinationType vaccinationType, int offset, int size, OrderSpecifier orderSpecifier) {
        return queryFactory.selectFrom(post)
            .leftJoin(post.user, user).fetchJoin()
            .where(vaccinationTypeEq(vaccinationType))
            .orderBy(orderSpecifier, post.createdAt.desc())
            .offset(offset)
            .limit(size)
            .fetch();
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
    public Optional<Post> findWithLikesByPostId(Long id) {
        Post post = queryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.likes.likes, like).fetchJoin()
            .where(QPost.post.id.eq(id))
            .orderBy(QPost.post.createdAt.desc())
            .fetchOne();

        return Optional.ofNullable(post);
    }

    @Override
    public Optional<Post> findWithCommentsByPostId(Long id) {
        Post post = queryFactory.selectFrom(QPost.post)
            .leftJoin(QPost.post.comments.comments, comment).fetchJoin()
            .where(QPost.post.id.eq(id))
            .orderBy(QPost.post.createdAt.desc())
            .fetchOne();

        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findByUserId(Long userId, int offset, int size) {
        return queryFactory.selectFrom(post)
            .leftJoin(post.user, user).fetchJoin()
            .where(post.user.id.eq(userId))
            .orderBy(post.createdAt.desc())
            .offset(offset)
            .limit(size)
            .fetch();
    }
}
