package com.cvi.comment.domain.repository;

import com.cvi.comment.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryDsl {
}
