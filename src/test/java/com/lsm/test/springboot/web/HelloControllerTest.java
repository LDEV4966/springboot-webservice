package com.lsm.test.springboot.web;

import com.lsm.test.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static  org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// @WebMvcTest : 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션
// 선언할 경우 @Controller, @ControllerAdvice 등을 사용가능
// 단, @Service, @Component, @Repository 등은 사용할 수 없다. 여기서는 컨트롤러만 사용하기 때문
@RunWith(SpringRunner.class) // 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
public class HelloControllerTest {

    @Autowired //스프링일 관리하는 Bean을 주입 받습니다.
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void hello가_리턴퇸다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello")) // MocMvc 를 통해 /hello 주소로 HTTP GET 요청을 합니다.
                .andExpect(status().isOk()) // HTTP Header의 status 결과를 검증. ex) 200(OK),404,500 등
                .andExpect(content().string(hello)); // Controller에서 "hello" 값이 정상적으로 return 되는지 검증
    }


    @Test
    @WithMockUser(roles = "USER")
    public void helloDto가_리턴퇸다() throws Exception{
        String name = "test";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        .param("name",name) // param은 API 테스트 할때, 사용될 요청 파라미터를 설정.
                        .param("amount",String.valueOf(amount))) //String만 허용
                .andExpect(status().isOk()) // HTTP Header의 status 결과를 검증. ex) 200(OK),404,500 등
                .andExpect(jsonPath("$.name",is(name)))// JsonPath은 json 응답값을 필드별로 검증할 수 있는 메소드입니다. $를 기준으로 필드명을 명시
                .andExpect(jsonPath("$.amount",is(amount)));
    }

}
