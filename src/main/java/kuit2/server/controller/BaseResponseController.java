package kuit2.server.controller;

import kuit2.server.common.response.BaseExceptionResponse;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.temp.UserData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.BAD_REQUEST;

@RestController
public class BaseResponseController {

    @RequestMapping("/base-response")
    public BaseResponse<UserData> checkBaseResponse() {
        UserData userData = new UserData("jude", 20);
        return new BaseResponse<>(userData);
    }

    @RequestMapping("/base-erroe-response")
    public BaseExceptionResponse checkBaseErrorResponse() {
        return new BaseExceptionResponse(BAD_REQUEST);
    }
}
