package kuit2.server.temp;

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
@RestController
public class TempController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJasonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messaggeBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messaggeBody);

        UserData userData = objectMapper.readValue(messaggeBody, UserData.class);
        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());

        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-json-v2")
    public void requestBodyJasonV1(@RequestBody UserData userData, HttpServletResponse response) throws IOException {

        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());

        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-json-v3")
    public String requestBodyJasonV1(@RequestBody UserData userData) {

        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());

        return "ok";
    }

    @PostMapping("/request-body-json-v4")
    public String requestBodyJasonV1(HttpEntity<UserData> httpEntity) {

        log.info("nickname={}, age={}", httpEntity.getBody().getNickname(), httpEntity.getBody().getAge());

        return "ok";
    }

    //ResponseBody

    @PostMapping("/response-body-json-v1")
    public UserData responseBodyJasonV1(@RequestBody UserData userData) {

        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());

        return userData;
    }

    @PostMapping("/response-body-json-v2")
    public HttpEntity<UserData> responseBodyJasonV2(@RequestBody UserData userData) {

        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());

        return new HttpEntity<>(userData);
    }

    //ResponseEntity는 HTTP Status Code를 지정할 수 있음
    @PostMapping("/response-body-json-v3")
    public ResponseEntity<UserData> responseBodyJasonV3(@RequestBody UserData userData) {

        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());

        return new ResponseEntity<>(userData, HttpStatus.BAD_REQUEST);
    }


    //정적으로 HTTP Response Status를 설정하는 애노테이션
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @PostMapping("/response-body-json-v4")
    public UserData responseBodyJasonV4(@RequestBody UserData userData) {

        log.info("nickname={}, age={}", userData.getNickname(), userData.getAge());

        return userData;
    }




}
