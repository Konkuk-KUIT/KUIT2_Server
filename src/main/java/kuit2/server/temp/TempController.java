package kuit2.server.temp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
//@Controller
@RestController  //responsebody 주석처리해도 결과 출력됨
public class TempController {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}",messageBody);

        UserData userData = objectMapper.readValue(messageBody, UserData.class);
        log.info("nickname={}, age={}",userData.getNickname(), userData.getAge());

        response.getWriter().write("ok1");
    }

    @PostMapping("/request-body-json-v2")
    public void requestBodyJsonV2(@RequestBody UserData userData, HttpServletResponse response) throws IOException{
        log.info("nickname={}, age={}",userData.getNickname(), userData.getAge());

        response.getWriter().write("ok2");
    }

    //@ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody UserData userData) {
        log.info("nickname={}, age={}",userData.getNickname(), userData.getAge());

        return "ok3";
    }

    //@ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<UserData> httpEntity) {
        log.info("nickname={}, age={}", httpEntity.getBody().getNickname(), httpEntity.getBody().getAge());

        return "ok4";
    }

    //@ResponseBody
    @PostMapping("/response-body-json-v1")
    public UserData responseBodyJsonV1(@RequestBody UserData userData) {
        log.info("nickname={}, age={}",userData.getNickname(), userData.getAge());

        return userData;
    }

    //@ResponseBody
    @PostMapping("/response-body-json-v2")
    public HttpEntity<UserData> responseBodyJsonV2(@RequestBody UserData userData) {
        log.info("nickname={}, age={}",userData.getNickname(), userData.getAge());

        return new HttpEntity<>(userData);
    }
    //@ResponseBody
    @PostMapping("/response-body-json-v3")
    public ResponseEntity<UserData> responseBodyJsonV3(@RequestBody UserData userData) {
        log.info("nickname={}, age={}",userData.getNickname(), userData.getAge());

        return new ResponseEntity<>(userData, HttpStatus.BAD_REQUEST);
    }

    //@ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @PostMapping("/response-body-json-v4")
    public UserData responseBodyJsonV4(@RequestBody UserData userData) {
        log.info("nickname={}, age={}",userData.getNickname(), userData.getAge());

        return userData;
    }
}
