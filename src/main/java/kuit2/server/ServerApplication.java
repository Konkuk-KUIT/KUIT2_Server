package kuit2.server;

import jakarta.validation.Validator;
import kuit2.server.controller.PostUserRequestValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

   //가 글로벌 은 거의 사용안함
//    @Override
//    public Validator getValidator(){
//        return new PostUserRequestValidator();
//    }

}
