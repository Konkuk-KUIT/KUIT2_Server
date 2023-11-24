package kuit2.server.temp;

// 요청을 받아들일 Controller

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
//@Controller
@RestController     // RestController 어노테이션 : 해당 컨트롤러에 모두 responseBody가 적용되는 효과가 있음
public class TempController {
    private final ObjectMapper objectMapper = new ObjectMapper();       // JSON data를 Object로 변환 -> spring에서 제공해주는 ObjectMapper 이용

    // requestBody
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();      // request의 InputStream을 읽어옴
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);     // 읽어온 InputStream을 통해 String형태의 message body를 읽어옴
        log.info("messageBody = {}", messageBody);      // messageBody를 잘 읽어왔는지 log를 출력해봄

        UserData userData = objectMapper.readValue(messageBody, UserData.class);        // 읽어온 string형태의 messageBody를 objectMapper를 통해 객체로 변환
        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());         // userData에 값이 잘 들어갔는지 log 출력

        response.getWriter().write("OK");           // 성공여부를 알 수 있도록 response에 "OK"를 적어줌
    }
    // host를 통해 보낸 JSON data가 objectMapper를 통해 userData에 저장 -> userData를 정확하게 읽으면 보낸 nickname, age를 알 수 있음 & "OK" 출력됨


    // V1에서는 InputStream을 읽고 messageBody를 추출하는 과정이 번거로움 -> spring에서 RequestBody를 통해 지원하고 있음!
    @PostMapping("/request-body-json-v2")
    public void requestBodyJsonV2(@RequestBody UserData userData, HttpServletResponse response) throws IOException {
        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());         // userData에 값이 잘 들어갔는지 log 출력

        response.getWriter().write("OK");           // 성공여부를 알 수 있도록 response에 "OK"를 적어줌
    }
    // 두 줄 만으로 V1과 똑같은 결과를 낼 수 있음


    // @ResponseBody 어노테이션을 통해 HttpServletResponse만을 사용하고 있는 코드를 바꿔보자(response에 "OK"를 적는 코드를 바꿔보자)
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody UserData userData) {
        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());         // userData에 값이 잘 들어갔는지 log 출력

        return "OK";
    }
    // HttpServletResponse 사용 X


    // reqeustBody를 사용하는것이 아니라 HttpEntity를 사용해보자
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity <UserData> httpEntity) {
        log.info("nickname={}, age={}", httpEntity.getBody().getNickname(), httpEntity.getBody().getAge());         // userData에 값이 잘 들어갔는지 log 출력

        return "OK";
    }
    // V1 ~ V4 : 모두 동일한 결과


    // responseBody
    @ResponseBody
    @PostMapping("/response-body-json-v1")
    public UserData responseBodyJsonV1(@RequestBody UserData userData) {
        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());         // userData에 값이 잘 들어갔는지 log 출력

        return userData;
    }
    // responseBody에서는 직접 입력받은 JSON data를 객체로 바꾸고 그 객체를 다시 return 해줌


    // V2 : return type으로 HttpEntity를 사용
    @ResponseBody
    @PostMapping("/response-body-json-v2")
    public HttpEntity<UserData> responseBodyJsonV2(@RequestBody UserData userData) {
        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());         // userData에 값이 잘 들어갔는지 log 출력

        return new HttpEntity<>(userData);
    }


    // V3 : return type으로 ResponseEntity를 사용
    // ResponseEntity : HttpStatus code를 지정할 수 있음
    @ResponseBody
    @PostMapping("/response-body-json-v3")
    public ResponseEntity<UserData> responseBodyJsonV3(@RequestBody UserData userData) {
        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());         // userData에 값이 잘 들어갔는지 log 출력

        return new ResponseEntity<>(userData, HttpStatus.BAD_REQUEST);          // V1,V2 : HttpStatus : 200, V3 : HttpStatus : 400
    }


    // V4 : 정적으로 HttpResponseStatus를 넣는 @ResponseStatus 어노테이션 사용
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)         // HttpStatus code로 400을 가짐
    @PostMapping("/response-body-json-v4")
    public UserData responseBodyJsonV4(@RequestBody UserData userData) {
        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());         // userData에 값이 잘 들어갔는지 log 출력

        return userData;
    }
}
