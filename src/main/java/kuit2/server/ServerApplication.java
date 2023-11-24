package kuit2.server;

import kuit2.server.controller.PostUserRequestValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    /*
    // 프로젝트내의 모든 controller에서 사용할 수 있는 global 검증기 등록 -> 이러면 다른 검증기를 등록할 수 없음 -> global 검증기 : 거의 사용하지X
    @Override
    public Validator getValidator(){
        return new PostUserRequestValidator();
    }

     */
}
