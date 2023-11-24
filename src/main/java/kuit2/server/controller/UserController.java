package kuit2.server.controller;

import kuit2.server.common.exception.UserException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.user.*;
import kuit2.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
@RequestMapping("/users")           // user 도메인의 api는 모두 users로 시작 -> userController에서 @RequestMapping 어노테이션으로 명시
public class UserController {

    private final UserService userService;          // controller는 service를 의존하고있으므로 의존관계 주입
//  private final PostUserRequestValidator postUserRequestValidator;            // 검증기를 사용할 수 있도록 의존관계 주입

    /*
    // local 검증기 -> 해당 controller에서만 검증기 사용가능하다는 뜻
    // => Bean Validation 방식을 이용하면 필요없음!!
    // Bean Validation 방식을 사용하면 PostUserRequest에서 검증 절차 거침(PostUserRequestValidator를 더이상 사용X)
    // -> 컨트롤러에서는 bindingResult에 error가 있는지만 확인하면 됨

    @InitBinder
    public void init(WebDataBinder dataBinder){
        dataBinder.addValidators(postUserRequestValidator);         // 해당 controller에 postUserRequestValidator가 등록됨
    }
    */


    /**
     * 회원 가입
     */
    @PostMapping("")            // 회원가입 api : "/users", http method : post -> request body값이 있음
    // PostUserRequest : 회원가입시 보낼 정보 -> dto dir 내부에 있음
    public BaseResponse<PostUserResponse> signUp(@Validated @RequestBody PostUserRequest postUserRequest, BindingResult bindingResult) {
        log.info("[UserController.signUp]");

        // service 호출전에 클라이언트에서 보내준 data들이 controller로 잘 들어왔는지 확인해야함 -> PostUserRequest의 형식에 잘 맞게 들어왔는지, null값은 아닌지 등등을 체크
        // 그러나 PostUserRequest의 모든 data를 체크할 수는 없음 -> spring에서 validation처리를 도와줌

        /*
          * FieldError, ObjectError를 사용하는 방식

        if(postUserRequest.getEmail() : 이메일 형식을 지키지 않은 경우){
            FieldError fieldError = new FieldError("postUserRequest", "email", "이메일 형식이 아닙니다.");
            bindingResult.addError(fieldError);         // fieldError를 bindingResult 객체에 넣어줌
        }
         -> 이런식이면 controller 내부에 validation을 위한 if문이 너무 많아지게됨
         -> controller에서 validation 부분을 분리할 필요가 있음
         => 이를 위해 Spring은 Validator 라는 인터페이스를 제공해줌
         => Validator 인터페이스를 구현한 검증기 만들어서 검증기에서 validation을 하도록 코드 변경 해야함
         */

        /*
        postUserRequestValidator.validate(postUserRequest, bindingResult);          // 검증기 호출 -> 스프링이 제공하는 @Validated 어노테이션으로 대체 가능!!
         */

        // 모든 검증이 끝난후 bindingResult에 errors가 있는지 없는지를 확인
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));       // userException 던져줌
        }
        return new BaseResponse<>(userService.signUp(postUserRequest));         // 실제로 회원가입로직을 실행할 userService를 호출
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
}
