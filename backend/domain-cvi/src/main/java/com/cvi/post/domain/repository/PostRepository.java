package com.cvi.post.domain.repository;

import com.cvi.post.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryDsl {

    void deleteAllByUserId(Long userId);
}
