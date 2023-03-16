package shop.mtcoding.getinthere.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import shop.mtcoding.getinthere.dto.JoinDto;
import shop.mtcoding.getinthere.dto.KakaoAccountDto;
import shop.mtcoding.getinthere.dto.TokenProperties;
import shop.mtcoding.getinthere.model.User;
import shop.mtcoding.getinthere.model.UserRepository;
import shop.mtcoding.getinthere.service.UserService;
import shop.mtcoding.getinthere.util.Fetch;

import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final HttpSession session;

    private final UserRepository userRepository;

    private final UserService userService;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/callback")
    public @ResponseBody String callback(String code) throws JsonProcessingException {
        // Open Auth -> 리소스의 오너 : 홍길동 / 리소스 서버 : 카카오 / Spring 서버 : 제 3자(클라이언트)
        // 리소스의 접근 권한을 Spring에게 위임함.
        
        // 1. code값 존재 유무 확인
        if (code == null || code.isEmpty()) {
            return "bad Request";
        }
        // 2. code 값 카카오 전달 -> access token 받기

        MultiValueMap<String, String> xForm = new LinkedMultiValueMap<>();
        xForm.add("grant_type", "authorization_code");
        xForm.add("client_id", "9ac8fd8165a3e8f7e8f3ab30b471d575");
        xForm.add("redirect_uri", "http://localhost:8080/callback");
        xForm.add("code", code);

        ResponseEntity<String> responseEntity = Fetch.kakao("https://kauth.kakao.com/oauth/token", HttpMethod.POST, xForm);
        // 3. access token으로 카카오의 홍길동 resource에 접근 가능해짐
        String responseBody = responseEntity.getBody();
        
        // 4. access token을 파싱
        ObjectMapper om = new ObjectMapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        TokenProperties tp = om.readValue(responseBody, TokenProperties.class);

        // 5. access token으로 email 정보 받기
        ResponseEntity<String> tokenEntity = Fetch.kakao("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, tp.getAccessToken());
        KakaoAccountDto kakaoDto = om.readValue(tokenEntity.getBody(), KakaoAccountDto.class);
        String userEmail = kakaoDto.getKakaoAccount().getEmail();

        // 6. 해당 email로 회원가입 되어 있는 user 정보가 있는지 DB 조회
        User principal = (User) userRepository.findByEmail(userEmail);

        // 7. 있으면 그 user 정보로 session 만들기. (자동로그인)
        if (principal != null) {
            session.setAttribute("principal", principal);
            System.out.println("세션 주입됨");
        }
        // 8. 없으면 강제 회원가입 시키고, 그 정보로 session 만들어주기. (자동로그인)
        String provider = "kakao_";
        String joinUsername = provider + userEmail;
        JoinDto joinDto = new JoinDto(joinUsername, "1234", userEmail, provider);
        userService.userJoin(joinDto);
        session.setAttribute("principal", principal);
        System.out.println("회원가입 후 세션 주입됨");
        return tokenEntity.getBody();
    }
}
