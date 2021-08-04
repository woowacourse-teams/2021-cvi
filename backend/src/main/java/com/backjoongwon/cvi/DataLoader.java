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

import java.util.Arrays;

import static com.backjoongwon.cvi.DummyData.DUMMY_DATA_10000;

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
            User 유저5 = User.builder().nickname("주모").ageRange(AgeRange.FIFTIES).build();
            User 유저6 = User.builder().nickname("똥강아지").ageRange(AgeRange.OVER_SIXTIES).build();
            User 유저1_shotVerified = User.builder()
                    .nickname(유저1.getNickname())
                    .ageRange(유저1.getAgeRange())
                    .shotVerified(true)
                    .profileUrl(유저1.getProfileUrl())
                    .build();
            유저1.update(유저1_shotVerified);
            User 유저3_shotVerified = User.builder()
                    .nickname(유저3.getNickname())
                    .ageRange(유저3.getAgeRange())
                    .shotVerified(true)
                    .profileUrl(유저3.getProfileUrl())
                    .build();
            유저3.update(유저3_shotVerified);
            userRepository.saveAll(Arrays.asList(유저1, 유저2, 유저3, 유저4, 유저5, 유저6));

            Comment 댓글1 = Comment.builder().content("댓글1").user(유저1).build();
            Comment 댓글2 = Comment.builder().content("댓글2").user(유저2).build();
            Comment 댓글3 = Comment.builder().content("댓글3").user(유저3).build();
            Comment 댓글4 = Comment.builder().content("댓글4").user(유저4).build();
            Comment 댓글5 = Comment.builder().content("댓글5").user(유저5).build();
            Comment 댓글6 = Comment.builder().content("댓글6").user(유저6).build();

            Like 좋아요1 = Like.builder().user(유저1).build();
            Like 좋아요2 = Like.builder().user(유저2).build();
            Like 좋아요3 = Like.builder().user(유저3).build();
            Like 좋아요4 = Like.builder().user(유저4).build();
            Like 좋아요5 = Like.builder().user(유저5).build();
            Like 좋아요6 = Like.builder().user(유저6).build();

            Post 글1 = Post.builder().content("백신을 맞았으니 이젠 쇼미더머니 부담없이 나갈 수 있겠어요!!!ㅎㅎ").user(유저1).vaccinationType(VaccinationType.ASTRAZENECA).build();
            Post 글2 = Post.builder().content("아스트라제네카는 사랑이에요... 제 첫사랑보다 강렬한 효과네요.").user(유저2).vaccinationType(VaccinationType.ASTRAZENECA).build();
            Post 글3 = Post.builder().content("사라졌던 입맛이 돌아왔네요. 화이자가 아니었다면 사랑스런 족발을 맛없게 먹을 뻔했어요").user(유저3).vaccinationType(VaccinationType.PFIZER).build();
            Post 글4 = Post.builder().content("얀센 1일차 후기. 우리집 똥개가 드디어 저한테 인사해주네요.").user(유저4).vaccinationType(VaccinationType.JANSSEN).build();
            Post 글5 = Post.builder().content("이제 여자친구 만들수있겠어요! 백신이 아니었다면 꿈도 못꿨을꺼에요!").user(유저5).vaccinationType(VaccinationType.MODERNA).build();
            Post 글6 = Post.builder().content("멍멍멍!! 멍멍멍!!  멍머어멍멍!!").user(유저6).vaccinationType(VaccinationType.MODERNA).build();
            Post 글7 = Post.builder().content(DUMMY_DATA_10000).user(유저2).vaccinationType(VaccinationType.MODERNA).build();
            postRepository.saveAll(Arrays.asList(글1, 글2, 글3, 글4, 글5, 글6, 글7));

            글1.assignComment(댓글1);
            글2.assignComment(댓글2);
            글3.assignComment(댓글3);
            글4.assignComment(댓글4);
            글5.assignComment(댓글5);
            글6.assignComment(댓글6);

            글1.addLike(좋아요1);
            글2.addLike(좋아요2);
            글3.addLike(좋아요3);
            글4.addLike(좋아요4);
            글5.addLike(좋아요5);
            글6.addLike(좋아요6);
        }
    }
}