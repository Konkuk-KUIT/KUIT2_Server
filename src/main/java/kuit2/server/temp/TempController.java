package kuit2.server.temp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.user.PostUserRequest;
import kuit2.server.dto.user.PostUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
//@Controller
@RestController
public class TempController {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info(messageBody);

        UserData userData = objectMapper.readValue(messageBody, UserData.class);
        log.info("nickname={}, name={}", userData.getNickname(), userData.getAge());

        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-json-v2")
    public void requestBodyJsonV2(@RequestBody UserData userData, HttpServletResponse response) throws IOException {
        log.info("nickname={}, name={}", userData.getNickname(), userData.getAge());

        response.getWriter().write("ok");
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody UserData userData) throws IOException {
        log.info("nickname={}, name={}", userData.getNickname(), userData.getAge());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<UserData> httpEntity) throws IOException {
        log.info("nickname={}, name={}", httpEntity.getBody().getNickname(), httpEntity.getBody().getAge());
        return "ok";
    }


    @ResponseBody
    @PostMapping("/response-body-json-v1")
    public UserData responseBodyJsonV1(@RequestBody UserData userData) throws IOException {
        log.info("nickname={}, name={}", userData.getNickname(), userData.getAge());
        return userData;
    }

    @ResponseBody
    @PostMapping("/response-body-json-v2")
    public HttpEntity responseBodyJsonV2(@RequestBody UserData userData) throws IOException {
        log.info("nickname={}, name={}", userData.getNickname(), userData.getAge());
        return new HttpEntity<>(userData);
    }

    @ResponseBody
    @PostMapping("/response-body-json-v3")
    public ResponseEntity responseBodyJsonV3(@RequestBody UserData userData) throws IOException {
        log.info("nickname={}, name={}", userData.getNickname(), userData.getAge());
        return new ResponseEntity<>(userData, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @PostMapping("/response-body-json-v4")
    public UserData responseBodyJsonV4(@RequestBody UserData userData) throws IOException {
        log.info("nickname={}, name={}", userData.getNickname(), userData.getAge());
        return userData;
    }

}