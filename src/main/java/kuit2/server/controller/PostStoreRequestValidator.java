package kuit2.server.controller;

import kuit2.server.dto.store.PostStoreRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

@Component
public class PostStoreRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz){
        return PostStoreRequest.class.isAssignableFrom(clazz);          // PostStoreRequest에 대한 지원여부 검사
    }

    @Override
    public void validate(Object target, Errors errors){

    }
}
