package kuit2.server.controller;

import kuit2.server.common.response.BaseErrorResponse;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.temp.UserData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.BAD_REQUEST;

@RestController
public class BaseResondeController {

    @RequestMapping("/base-response")
    public BaseResponse<UserData> checkBaseReponse(){
        UserData userData = new UserData("jude",20);
        return new BaseResponse<>(userData);
    }
    @RequestMapping("/base-error-response")
    public BaseErrorResponse checkErrorReponse(){
        UserData userData = new UserData("jude",20);
        return new BaseErrorResponse(BAD_REQUEST);
    }


}
