package kuit2.server.util;

import org.springframework.validation.BindingResult;

public class BindingResultUtils {

    // 해당 bindingResult에 있는 error 메시지를 보기 편하게 파싱해주는 메소드
    public static String getErrorMessages(BindingResult bindingResult) {
        StringBuilder errorMessages = new StringBuilder();
        bindingResult.getAllErrors()
                .forEach(error -> errorMessages.append(error.getDefaultMessage()).append(". "));
        return errorMessages.toString();
    }

}