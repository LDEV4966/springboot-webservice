package com.lsm.test.springboot.config.auth;

import com.lsm.test.springboot.config.auth.dto.OAuthAttributes;
import com.lsm.test.springboot.config.auth.dto.SessionUser;
import com.lsm.test.springboot.domain.user.User;
import com.lsm.test.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //현재 로그인 진행중인 서비스를 구분하는 코드입니다. (ex, 구글, 네이버, 카카오...)
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        // 로그인 진행시 키가 되는 필드값을 이야기합니다. PK와 같은 의미로 이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용됩니다.
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스입니다.
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId,userNameAttributeName,oAuth2User.getAttributes());

        // 유저정보 최신화
        User user = saveOrUpdate(attributes);

        //세션 등록
        httpSession.setAttribute("user",new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes){

        User user = userRepository.findByEmail(attributes.getEmail())
                .map( entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}
