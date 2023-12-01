package kuit2.server.util;

import kuit2.server.dto.user.PostUserResponse;
import org.springframework.validation.BindingResult;

public class BindingResultUtils {

//    public PostUserResponse signUp()

    public static String getErrorMessages(BindingResult bindingResult) {
        StringBuilder errorMessages = new StringBuilder();
        bindingResult.getAllErrors()
                .forEach(error -> errorMessages.append(error.getDefaultMessage()).append(". "));
        return errorMessages.toString();
    }

}