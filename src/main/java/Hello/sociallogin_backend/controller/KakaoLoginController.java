package Hello.sociallogin_backend.controller;


import Hello.sociallogin_backend.service.KakaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:3000") // 허용할 Origin을 지정합니다.
public class KakaoLoginController {
    @Value("${kakao.client_id}") //yml파일에 적어 놓은 client_id >> REST API 키
    private String client_id;

    @Autowired
    private KakaoService kakaoService;

    @GetMapping("/kakao/callback")
    public String callback(@RequestParam("code") String code) {
        try {
            String accessToken = kakaoService.getAccessTokenFromKakao(client_id, code);
            System.out.println("accessToken이다아ㅏ:" + accessToken);
            HashMap<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
            //User 로그인, 또는 회원가입 로직 추가
            // Jackson 라이브러리를 사용하여 Java 객체를 JSON 문자열로 변환하는 방법
//            먼저, ObjectMapper 객체를 생성
//            ObjectMapper는 Jackson 라이브러리에서 제공하는 클래스로, Java 객체를 JSON 문자열로 변환하거나 JSON 문자열을 Java 객체로 변환하는 등의 작업을 수행하는데 사용됨.
            ObjectMapper mapper = new ObjectMapper();

//            Java 객체를 JSON 문자열로 변환: ObjectMapper의 writeValueAsString 메서드를 사용하여 Java 객체를 JSON 문자열로 변환
//            이 메서드는 주어진 객체를 JSON 형식의 문자열로 직렬화 함.
            String userInfoJson = mapper.writeValueAsString(userInfo);
//            JSON 문자열 반환: 변환된 JSON 문자열을 메서드의 반환값으로 사용하여 클라이언트에게 전송
            return userInfoJson;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while getting access token";
        }
    }

}
