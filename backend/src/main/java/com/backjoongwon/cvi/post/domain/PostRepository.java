package com.backjoongwon.cvi.post.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryDsl {

    void deleteAllByUserId(Long userId);

    @EntityGraph(attributePaths = {"comments"})
    Optional<Post> findWithCommentsById(Long aLong);
}
