package com.backjoongwon.cvi.post.service;

import com.backjoongwon.cvi.aws.s3.AwsS3Uploader;
import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.comment.domain.CommentRepository;
import com.backjoongwon.cvi.common.exception.NotFoundException;
import com.backjoongwon.cvi.common.exception.UnAuthorizedException;
import com.backjoongwon.cvi.image.domain.Image;
import com.backjoongwon.cvi.image.domain.ImageRepository;
import com.backjoongwon.cvi.image.dto.ImageRequest;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.*;
import com.backjoongwon.cvi.post.dto.PostRequest;
import com.backjoongwon.cvi.post.dto.PostResponse;
import com.backjoongwon.cvi.post.dto.PostWithCommentResponse;
import com.backjoongwon.cvi.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    @Value("${aws.s3.directory.path.posts.images}")
    private String awsS3PostsImagesDirPath;

    private final ImageConverter imageConverter;
    private final AwsS3Uploader awsS3Uploader;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public PostResponse create(Optional<User> optionalUser, PostRequest postRequest) {
        validateSignedin(optionalUser);
        User writer = optionalUser.get();
        Post post = postRequest.toEntity();
        post.assignUser(writer);
        postRepository.save(post);
        assignImages(post, postRequest.getImages());
        return PostResponse.of(post, writer);
    }

    private void assignImages(Post post, List<ImageRequest> imageRequests) {
        if (imageRequests.isEmpty()) {
            return;
        }
        final List<String> imageUrls = getUploadedImageUrls(imageRequests);
        assignPostToImages(post, imageUrls);
    }

    private List<String> getUploadedImageUrls(List<ImageRequest> imageRequests) {
        final List<String> imageUrls = new ArrayList<>();
        for (ImageRequest imageRequest : imageRequests) {
            final ImageFile imageFile = imageConverter.convertBytesToImageFile(imageRequest.getData(), imageRequest.getType());
            final File file = imageFile.getFile();
            final String imageUrl = awsS3Uploader.upload(awsS3PostsImagesDirPath, file);
            imageFile.delete();
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    private List<Image> assignPostToImages(Post post, List<String> imageUrls) {
        final List<Image> images = new ArrayList<>();
        for (String imageUrl : imageUrls) {
            final Image image = Image.builder()
                    .url(imageUrl)
                    .build();
            image.assignPost(post);
            imageRepository.save(image);
            images.add(image);
        }
        return images;
    }

    @Transactional
    public PostResponse findById(Long id, Optional<User> optionalUser) {
        Post post = findPostByPostId(id);
        post.increaseViewCount();
        return createPostResponse(optionalUser, post);
    }

    private PostResponse createPostResponse(Optional<User> optionalUser, Post post) {
        return PostResponse.of(post, optionalUser.orElse(null));
    }

    public List<PostResponse> findByVaccineType(VaccinationType vaccinationType, Optional<User> optionalUser) {
        List<Post> posts = postRepository.findByVaccineType(vaccinationType);
        return PostResponse.toList(posts, optionalUser.orElse(null));
    }

    public List<PostResponse> findByVaccineType(VaccinationType vaccinationType, int offset, int size, Sort sort, int hours, Optional<User> optionalUser) {
        List<Post> posts = postRepository.findByVaccineType(vaccinationType, offset, size, Sort.toOrderSpecifier(sort), hours);
        return PostResponse.toList(posts, optionalUser.orElse(null));
    }

    public List<PostResponse> searchByTypesAndQuery(VaccinationType vaccinationType, SearchType searchType, String q, Optional<User> optionalUser) {
        List<Post> posts = postRepository.searchBbyTypesAndQuery(vaccinationType, searchType, q);
        return PostResponse.toList(posts, optionalUser.orElse(null));
    }

    @Transactional
    public void update(Long id, Optional<User> optionalUser, PostRequest postRequest) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        Post post = findPostByPostId(id);
        updateImages(post, postRequest.getImages());
        post.updateContent(postRequest.toEntity(), user);
    }

    private void updateImages(Post post, List<ImageRequest> imageRequests) {
        deleteAllImagesInPost(post);
        if (imageRequests.isEmpty()) {
            return;
        }
        assignImages(post, imageRequests);
    }

    private void deleteAllImagesInPost(Post post) {
        deleteImagesFromAwsS3(post.getS3PathsOfAllImages());
        imageRepository.deleteAll(post.getAllImagesAsList());
    }

    private void deleteImagesFromAwsS3(List<String> s3PathsToDelete) {
        for (String path : s3PathsToDelete) {
            awsS3Uploader.delete(awsS3PostsImagesDirPath, path);
        }
    }

    @Transactional
    public void delete(Long id, Optional<User> optionalUser) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        Post post = findPostByPostId(id);
        post.validateAuthor(user);
        deleteImagesFromAwsS3(post.getS3PathsOfAllImages());
        imageRepository.deleteAll(post.getAllImagesAsList());
        postRepository.deleteById(id);
    }

    private Post findPostByPostId(Long id) {
        validateNotNull(id);
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 id의 게시글이 존재하지 않습니다."));
    }

    private void validateSignedin(Optional<User> user) {
        if (!user.isPresent()) {
            throw new UnAuthorizedException("인증되지 않은 사용자입니다.");
        }
    }

    private void validateNotNull(Long id) {
        if (Objects.isNull(id)) {
            log.info("id는 null이 될 수 없습니다.");
            throw new NotFoundException("id는 null이 될 수 없습니다.");
        }
    }

    public List<PostWithCommentResponse> findByUserAndFilter(Optional<User> optionalUser, Filter filter) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        return createPostsResponseByFilter(filter, user);
    }

    private List<PostWithCommentResponse> createPostsResponseByFilter(Filter filter, User user) {
        if (filter == Filter.LIKES) {
            return createResponsesFilteredByLikes(user);
        }
        if (filter == Filter.COMMENTS) {
            return createResponsesFilteredByComments(user);
        }
        List<Post> posts = postRepository.findByUserId(user.getId());
        return PostWithCommentResponse.toList(posts, user);
    }

    private List<PostWithCommentResponse> createResponsesFilteredByLikes(User user) {
        List<Like> likes = likeRepository.findByUserId(user.getId());
        List<Post> posts = likes.stream()
                .map(Like::getPost)
                .collect(Collectors.toList());
        return PostWithCommentResponse.toList(posts, user);
    }

    private List<PostWithCommentResponse> createResponsesFilteredByComments(User user) {
        List<Comment> comments = commentRepository.findByUserId(user.getId());
        List<Post> posts = comments.stream()
                .map(Comment::getPost)
                .distinct()
                .collect(Collectors.toList());
        return PostWithCommentResponse.toList(posts, user);
    }

    public List<PostWithCommentResponse> findByUserAndFilter(Filter filter, int offset, int size, Optional<User> optionalUser) {
        validateSignedin(optionalUser);
        User user = optionalUser.get();
        if (filter == Filter.LIKES) {
            return createResponsesFilteredByLikes(user, offset, size);
        }
        if (filter == Filter.COMMENTS) {
            return createResponsesFilteredByComments(user, offset, size);
        }
        List<Post> posts = postRepository.findByUserId(user.getId(), offset, size);
        return PostWithCommentResponse.toList(posts, user);
    }

    private List<PostWithCommentResponse> createResponsesFilteredByLikes(User user, int offset, int size) {
        List<Like> likes = likeRepository.findByUserId(user.getId(), offset, size);
        List<Post> posts = likes.stream()
                .map(Like::getPost)
                .collect(Collectors.toList());
        return PostWithCommentResponse.toList(posts, user);
    }

    private List<PostWithCommentResponse> createResponsesFilteredByComments(User user, int offset, int size) {
        List<Comment> comments = commentRepository.findByUserId(user.getId(), offset, size);
        List<Post> posts = comments.stream()
                .map(Comment::getPost)
                .distinct()
                .collect(Collectors.toList());
        return PostWithCommentResponse.toList(posts, user);
    }
}
