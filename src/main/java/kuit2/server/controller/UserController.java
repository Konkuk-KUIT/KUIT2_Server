package kuit2.server.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit2.server.common.exception.UserException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.user.*;
import kuit2.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;
import static kuit2.server.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 회원 가입
     */
    @PostMapping("")
    public BaseResponse<PostUserResponse> signUp(@Validated @RequestBody PostUserRequest postUserRequest, BindingResult bindingResult) {
        log.info("[UserController.signUp]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(userService.signUp(postUserRequest));
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public BaseResponse<Object> login(@Validated @RequestBody PostLoginRequest postLoginRequest, BindingResult bindingResult) {
        log.info("[UserController.login]");

        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(userService.login(postLoginRequest));

    }

    /**
     *
     * 회원 단건 조회
     */
    @GetMapping("/{userId}")
    public BaseResponse<GetUserResponse> getUserInfo(@PathVariable long userId) {
        log.info("[UserController.getUserInfo]");

        return new BaseResponse<>(userService.getUserInfo(userId));
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

    /**
     * 유저장바구니 조회
     */
    @GetMapping("/{userId}/cart")
    public BaseResponse<List<GetCartResponse>> getCart(@PathVariable long userId) {
        log.info("[UserController.getCart");

        return new BaseResponse<List<GetCartResponse>>(userService.getCart(userId));
    }

    /**
     * 회원이 쓴 리뷰 조회
     */
    @GetMapping("/{userId}/reviews")
    public BaseResponse<List<GetReviewResponse>> getReviews(@PathVariable long userId) {
        log.info("UserController.getReviews");

        return new BaseResponse<List<GetReviewResponse>>(userService.getReviews(userId));
    }

    /**
     * 찜하기
     */
    @PostMapping("/{userId}/favorites")
    public BaseResponse<Object> addFavorite(@PathVariable long userId, @Validated @RequestBody PostFavoriteRequest postFavoriteRequest, BindingResult bindingResult) {
        log.info("UserController.addFavorite");

        userService.addFavorite(userId, postFavoriteRequest.getRestaurantId());

        return new BaseResponse<>(SUCCESS);
    }

    /**
     * 찜 취소
     */
    @DeleteMapping("/{userId}/favorites")
    public BaseResponse<Object> deleteFavorite(@PathVariable long userId, @RequestParam long restaurantId) {
        log.info("UserController.deleteFavorite");

        userService.deleteFavorite(userId, restaurantId);

        return new BaseResponse<>(SUCCESS);
    }

    /**
     * 찜 목록 조회
     */
    @GetMapping("/{userId}/favorites")
    public BaseResponse<GetFavoriteResponse> getFavorites(@PathVariable long userId) {
        log.info("UserController.getFavorites");

        return new BaseResponse<GetFavoriteResponse>(userService.getFavorites(userId));
    }
}
