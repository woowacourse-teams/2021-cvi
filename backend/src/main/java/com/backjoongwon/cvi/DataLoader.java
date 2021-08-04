package com.backjoongwon.cvi;

import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Profile("local&!test")
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.findAll().isEmpty()) {
            User 유저1 = User.builder().nickname("검프").ageRange(AgeRange.TEENS).build();
            User 유저2 = User.builder().nickname("욘").ageRange(AgeRange.TWENTIES).build();
            User 유저3 = User.builder().nickname("라이언").ageRange(AgeRange.THIRTIES).build();
            User 유저4 = User.builder().nickname("엘라").ageRange(AgeRange.FORTIES).build();

            유저1.makeVerified();
            유저3.makeVerified();
            userRepository.saveAll(Arrays.asList(유저1, 유저2, 유저3, 유저4));

            List<Post> posts = new ArrayList<>();
            Random random = new Random();
            int randomInt;

            for (int i = 0; i < 1000; i++) {
                randomInt = random.nextInt(1000);
                if (randomInt % 4 == 0) {
                    Post post = Post.builder().content("글" + (i + 1)).vaccinationType(VaccinationType.PFIZER).user(유저1).build();
                    post.addLike(Like.builder().user(유저1).build());
                    post.assignComment(Comment.builder().content("댓글" + randomInt).user(유저1).build());
                    posts.add(post);

                }
                if (randomInt % 4 == 1) {
                    Post post = Post.builder().content("글" + (i + 1)).vaccinationType(VaccinationType.ASTRAZENECA).user(유저2).build();
                    post.addLike(Like.builder().user(유저2).build());
                    post.assignComment(Comment.builder().content("댓글" + randomInt).user(유저2).build());
                    posts.add(post);

                }
                if (randomInt % 4 == 2) {
                    Post post = Post.builder().content("글" + (i + 1)).vaccinationType(VaccinationType.JANSSEN).user(유저3).build();
                    post.addLike(Like.builder().user(유저3).build());
                    post.assignComment(Comment.builder().content("댓글" + randomInt).user(유저3).build());
                    posts.add(post);

                }
                if (randomInt % 4 == 3) {
                    Post post = Post.builder().content("글" + (i + 1)).vaccinationType(VaccinationType.MODERNA).user(유저4).build();
                    post.addLike(Like.builder().user(유저4).build());
                    post.assignComment(Comment.builder().content("댓글" + randomInt).user(유저4).build());
                    posts.add(post);

                }
            }
            postRepository.saveAll(posts);
        }
    }
}
