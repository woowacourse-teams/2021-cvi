package com.backjoongwon.cvi.comment.application;

import com.backjoongwon.cvi.comment.domain.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DisplayName("댓글 비지니스 흐름 테스트")
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    //생성

    //수정

    //삭제


}