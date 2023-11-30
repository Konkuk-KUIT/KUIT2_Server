package kuit2.server.controller;


import kuit2.server.dto.user.PostUserRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostUserRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

        /*PostUserRequest postUserRequest = (PostUserRequest) target

        if(postUserRequest.getEmail() : 이메일 형식을 지키지 않은 경우) {
            errors.rejectValue("postUserRequest", "email", "이메일 형식이 아닙니다.");
        }
        if(postUserRequest.get() : 이메일 형식을 지키지 않은 경우) {
            errors.rejectValue("postUserRequest", "email", "이메일 형식이 아닙니다.");
        }
        if(postUserRequest.getEmail() : 이메일 형식을 지키지 않은 경우) {
            errors.rejectValue("postUserRequest", "email", "이메일 형식이 아닙니다.");
        }
        if(postUserRequest.getEmail() : 이메일 형식을 지키지 않은 경우) {
            errors.rejectValue("postUserRequest", "email", "이메일 형식이 아닙니다.");
        }*/
    }
}
