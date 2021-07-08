package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    public PostResponse create(Long userId, PostRequest postRequest) {
        return null;
    }

    public void update(Long userId, Long postId, PostRequest postRequest) {
    }

    public void delete(Long postId, Long userId) {

    }
}
