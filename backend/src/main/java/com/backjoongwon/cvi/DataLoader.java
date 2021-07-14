package com.backjoongwon.cvi;

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
import java.util.List;

import static com.backjoongwon.cvi.DummyData.DUMMY_DATA_10000;

@Component
@RequiredArgsConstructor
@Profile("!test & !prod")
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.findAll().isEmpty()) {
            List<User> users = Arrays.asList(User.builder().nickname("검프").ageRange(AgeRange.TEENS).build(),
                    User.builder().nickname("욘").ageRange(AgeRange.TWENTIES).build(),
                    User.builder().nickname("라이언").ageRange(AgeRange.THIRTIES).build(),
                    User.builder().nickname("엘라").ageRange(AgeRange.FORTIES).build(),
                    User.builder().nickname("주모").ageRange(AgeRange.FIFTIES).build(),
                    User.builder().nickname("똥강아지").ageRange(AgeRange.OVER_SIXTIES).build());

            List<Post> posts = postRepository.saveAll(
                    Arrays.asList(
                            Post.builder().content("백신을 맞았으니 이젠 쇼미더머니 부담없이 나갈 수 있겠어요!!")
                                    .vaccinationType(VaccinationType.ASTRAZENECA).build(),
                            Post.builder().content("아스트라제네카는 사랑이에요... 제 첫사랑보다 강렬한 효과네요.")
                                    .vaccinationType(VaccinationType.ASTRAZENECA).build(),
                            Post.builder().content("사라졌던 입맛이 돌아왔네요. 화이자가 아니었다면 사랑스런 족발을 맛없게 먹을 뻔했어요")
                                    .vaccinationType(VaccinationType.PFIZER).build(),
                            Post.builder().content("얀센 1일차 후기. 우리집 똥개가 드디어 저한테 인사해주네요 ")
                                    .vaccinationType(VaccinationType.JANSSEN).build(),
                            Post.builder().content("이제 여자친구 만들수있겠어요! 백신이 아니었다면 꿈도 못꿨을꺼에요!")
                                    .vaccinationType(VaccinationType.MODERNA).build(),
                            Post.builder().content("멍멍멍!! 멍멍멍!!  멍머어멍멍!!")
                                    .vaccinationType(VaccinationType.MODERNA).build(),
                            Post.builder().content(DUMMY_DATA_10000)
                                    .vaccinationType(VaccinationType.MODERNA).build()
                    )
            );
            for (int idx = 0; idx < posts.size(); idx++) {
                posts.get(idx).assignUser(users.get(idx % users.size()));
            }
        }
    }
}
