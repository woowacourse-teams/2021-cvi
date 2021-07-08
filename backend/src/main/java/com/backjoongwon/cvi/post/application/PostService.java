package com.backjoongwon.cvi.post.application;

import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    public PostResponse create(Long userId, PostRequest postRequest) {
        return null;
    }

    public PostResponse find(Long postId) {
        return null;
    }

    public List<PostResponse> findAll() {
        return null;
    }

    public void update(Long userId, Long postId, PostRequest postRequest) {
    }

    public void delete(Long postId, Long userId) {
    }
}
