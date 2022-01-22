package com.lsm.test.springboot.config.auth;

import com.lsm.test.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //crsf 자동 보안 해제
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()// url 별 권한 관리를 설정하는 옵션의 시작점. 선언 이후 antMatchers 사용가능
                .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**") // 권한관리 대상(URL,HTTP 메소드별)
                .permitAll() // 권한 다 주기
                .antMatchers("/api/v1/**")// 권한관리 대상
                .hasRole(Role.USER.name()) // "/api/v1/**" 의 주소를 가진 api 호출은 USER 권한을 가진 사람만 가능
                .anyRequest()// 설정 값 이 외의 호출들
                .authenticated()// 로그인 된 사용자들에게만 허용하겠다.
                .and()
                .logout() //로그아웃시,
                .logoutSuccessUrl("/") // 성공시 이동
                .and()
                .oauth2Login() // 소셜로그인시,
                .userInfoEndpoint() // 소셜 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당한다.
                .userService(customOAuth2UserService); // 소셜로그인 성공시 후속조치를 진행할 UserService 인터페이스에 구현체를 등록한다.

    }
}
