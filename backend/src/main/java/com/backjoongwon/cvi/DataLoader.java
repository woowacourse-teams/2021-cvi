package com.backjoongwon.cvi;

import com.backjoongwon.cvi.comment.domain.Comment;
import com.backjoongwon.cvi.like.domain.Like;
import com.backjoongwon.cvi.like.domain.LikeRepository;
import com.backjoongwon.cvi.post.domain.Post;
import com.backjoongwon.cvi.post.domain.PostRepository;
import com.backjoongwon.cvi.post.domain.VaccinationType;
import com.backjoongwon.cvi.user.domain.AgeRange;
import com.backjoongwon.cvi.user.domain.User;
import com.backjoongwon.cvi.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
@Profile("local&!test")
public class DataLoader implements CommandLineRunner {

    private static final int USER_COUNT = 50;
    private static final int POST_COUNT = 300;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final Random random = new Random();

    @Autowired
    private final EntityManager em;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.findAll().isEmpty()) {
            List<User> users = new ArrayList<>();
            for (int i = 0; i < USER_COUNT; i++) {
                User user = User.builder().nickname(String.valueOf(i)).ageRange(AgeRange.FIFTIES).build();
                users.add(user);

                User updateUser = User.builder().nickname(user.getNickname()).ageRange(AgeRange.FORTIES).shotVerified(true).build();
                user.update(updateUser);
            }









            userRepository.saveAll(users);

            List<Post> posts = new ArrayList<>();
            for (int i = 0; i < POST_COUNT; i++) {
                int randomInt = random.nextInt(USER_COUNT);
                User user = users.get(i % USER_COUNT);
                if (randomInt % 4 == 0) {
                    Post post = Post.builder().content("글" + (i + 1)).vaccinationType(VaccinationType.PFIZER).user(user).build();
                    putLikeAndCommentRandomly(users, USER_COUNT, post, random);
                    posts.add(post);
                }
                if (randomInt % 4 == 1) {
                    Post post = Post.builder().content("글" + (i + 1)).vaccinationType(VaccinationType.ASTRAZENECA).user(user).build();
                    posts.add(post);
                }
                if (randomInt % 4 == 2) {
                    Post post = Post.builder().content("글" + (i + 1)).vaccinationType(VaccinationType.JANSSEN).user(user).build();
                    posts.add(post);
                }
                if (randomInt % 4 == 3) {
                    Post post = Post.builder().content("글" + (i + 1)).vaccinationType(VaccinationType.MODERNA).user(user).build();
                    posts.add(post);
                }
            }
            postRepository.saveAll(posts);

            for (Post post : posts) {
                Long id = post.getId();
                Query q = em.createNativeQuery("UPDATE post SET created_at=:created_at WHERE post_id=:post_id");
                q.setParameter("created_at", generateRandomLocalDateTime());
                q.setParameter("post_id", id);
                q.executeUpdate();
            }
        }
    }

    private void putLikeAndCommentRandomly(List<User> users, int userCount, Post post, Random random) {
        Set<User> userSet = new HashSet<>();

        for (int j = 0; j < random.nextInt(10); j++) {
            int randomUser = random.nextInt(userCount);
            userSet.add(users.get(randomUser));
        }

        for (User user : userSet) {
            Like like = Like.builder().user(user).build();
            post.addLike(like);
            post.assignComment(Comment.builder().content("댓글").user(user).build());
            likeRepository.save(like);
        }
    }

    private Timestamp generateRandomLocalDateTime() {
        int randomHour = random.nextInt(24);
        int randomMins = random.nextInt(60);
        int randomDays = random.nextInt(20);
        LocalDateTime localDateTime = LocalDateTime.now().minusHours(randomHour).minusMinutes(randomMins).minusDays(randomDays);
        return Timestamp.valueOf(localDateTime);
    }
}
