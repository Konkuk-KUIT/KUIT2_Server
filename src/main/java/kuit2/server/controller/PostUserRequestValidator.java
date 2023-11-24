package kuit2.server.controller;

import kuit2.server.dto.user.PostUserRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostUserRequestValidator implements Validator {
    // 지원여부 확인
    @Override
    public boolean supports(Class<?> clazz){
        return PostUserRequest.class.isAssignableFrom(clazz);           // PostUserRequest에 대한 지원 여부를 확인
    }

    // 실제 예외처리 -> controller에서 if문으로 예외처리하는 부분을 이 메서드로 옮김
    @Override
    public void validate(Object target, Errors errors){
        PostUserRequest postUserRequest = (PostUserRequest) target;
        /*
        if(postUserRequest.getEmail() : 이메일 형식을 지키지 않은 경우){
            errors.rejectValue("postUserRequest", "email", "이메일 형식이 아닙니다.");
        }


         */
        // 검증할 내용들 여기에다가 if문으로 분기처리하면 됨
    }
}
