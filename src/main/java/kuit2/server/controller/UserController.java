package kuit2.server.controller;

import kuit2.server.common.exception.UserException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dao.UserDao;
import kuit2.server.dto.user.*;
import kuit2.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_USER_STATUS;
import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_USER_VALUE;
import static kuit2.server.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
//    private final PostUserRequestValidator postUserRequestValidator;

    /**
     * 로컬 검증
     * user 경로 안에서만 검증 사용
     * */
//    @InitBinder
//    public void init(WebDataBinder dataBinder){
//        dataBinder.addValidators(postUserRequestValidator);
//    }
    /**
     * 회원 가입
     */
    @PostMapping("")
    public BaseResponse<PostUserResponse> signUp(@Validated @RequestBody PostUserRequest postUserRequest, BindingResult bindingResult) {
        log.info("[UserController.signUp]");
        if (bindingResult.hasErrors()) {

            throw new RuntimeException(bindingResult.getAllErrors().toString());
        }
        return new BaseResponse<>(userService.signUp(postUserRequest));
    }

    /**
     * 회원 휴면
     */
    @PatchMapping("/{userId}/dormant")
    public BaseResponse<Object> modifyUserStatus_dormant(@PathVariable long userId) {
        log.info("[UserController.modifyUserStatus_dormant]");
        userService.modifyUserStatus_dormant(userId);
        return new BaseResponse<>(null);
    }

    /**
     * 회원 탈퇴
     */
    @PatchMapping("/{userId}/deleted")
    public BaseResponse<Object> modifyUserStatus_deleted(@PathVariable long userId) {
        log.info("[UserController.modifyUserStatus_delete]");
        userService.modifyUserStatus_deleted(userId);
        return new BaseResponse<>(null);
    }

    /**
     * 닉네임 변경
     */
    @PatchMapping("/{userId}/nickname")
    public BaseResponse<String> modifyNickname(@PathVariable long userId,
                                               @Validated @RequestBody PatchNicknameRequest patchNicknameRequest, BindingResult bindingResult) {
        log.info("[UserController.modifyNickname]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        userService.modifyNickname(userId, patchNicknameRequest.getNickname());
        return new BaseResponse<>(null);
    }

    /**
     * 회원 목록 조회
     */
    @GetMapping("")
    public BaseResponse<List<GetUserResponse>> getUsers(
            @RequestParam(required = false, defaultValue = "") String nickname,
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(required = false, defaultValue = "active") String status) {
        log.info("[UserController.getUsers]");
        if (!status.equals("active") && !status.equals("dormant") && !status.equals("deleted")) {
            throw new UserException(INVALID_USER_STATUS);
        }
        return new BaseResponse<>(userService.getUsers(nickname, email, status));
    }

    @GetMapping("/{userId}/email")
    public BaseResponse<List<GetUserResponse>> getUserEmail(@PathVariable long userId) {
        log.info("[UserController.getUserEmail]");

        // userId에 대한 유효성 검사
        if (userId <= 0) {
            throw new UserException(INVALID_USER_VALUE, "Invalid user ID");
        }

        return new BaseResponse(userService.getEmailByUserId(userId));
    }



    /**
     * 회원 등급 조회
     */
    @GetMapping("/{userId}/grade")
    public BaseResponse<String> getUserGrade(@PathVariable long userId){
        log.info("[UserController.getUserGrade]");

        String userGrade =  userService.getUserGrade(userId);
        return new BaseResponse<>(userGrade);
    }

    /**
     * 회원 주문 횟수 조회
     * */
    @GetMapping("/{userId}/order_count")
    public BaseResponse<String> getOrderCount(@PathVariable long userId){
        log.info("[UserController.getUserGrade]");

        int orderCount =  userService.getUserOrderCount(userId);
        return new BaseResponse(orderCount);
    }

    @PostMapping("/{userId}/order_count")
    public BaseResponse<String> increaseOrderCount(@PathVariable long userId){
        log.info("[UserController.getUserGrade]");

        int orderCount =  userService.increaseUserOrderCount(userId);
        return new BaseResponse(orderCount);
    }



}
