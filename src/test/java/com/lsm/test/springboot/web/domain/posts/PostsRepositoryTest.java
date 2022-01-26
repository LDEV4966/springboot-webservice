package com.lsm.test.springboot.web.domain.posts;

import com.lsm.test.springboot.domain.posts.Posts;
import com.lsm.test.springboot.domain.posts.PostsRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest // 별다른 설정없이 @SpringBootTest 를 사용할 경우 H2(인메모리) 데이터베이스를 사용한다.
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    //Junit에서 단위 테스트가 끝날때 마다 수행되는 메소드를 지정
    // 보통은 배포전 전체 테스트를 수행알 때 테스트간의 데이터 침범을 막기위해 사용
    // 여러 테스트가 동시에 수행되면 테스트용 DB인 H2 Data가 그대로 남아있어 다음 테스트 실행시 테스트가 실패할 수 있다.
    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // 테이블 posts에 insert/update 쿼리를 시작. id 값이 이미 존재하면 update 아니면, insert 실행
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("lsm@gmail.com")
                .build());

        //when
        //모든데이터 조회
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        Assertions.assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>>>>>>> createData = "+posts.getCreateDate()+", modifiedDate = " + posts.getModifiedDate());

        Assertions.assertThat(posts.getCreateDate()).isAfter(now);
        Assertions.assertThat(posts.getModifiedDate()).isAfter(now);
    }


}
