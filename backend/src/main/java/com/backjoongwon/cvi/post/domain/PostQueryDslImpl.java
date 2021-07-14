package com.backjoongwon.cvi.post.domain;

import com.backjoongwon.cvi.user.domain.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

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
                .fetch();
    }

    private BooleanExpression vaccinationTypeEq(VaccinationType vaccinationType) {
        return Objects.nonNull(vaccinationType) ? post.vaccinationType.eq(vaccinationType) : null;
    }
}
