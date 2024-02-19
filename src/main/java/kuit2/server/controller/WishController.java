package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.wish.GetWishListResponse;
import kuit2.server.service.WishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/wish")
public class WishController {
    private final WishService wishService;

    /**
     * 찜 리스트 조회
     */
    @GetMapping("")
    public BaseResponse<List<GetWishListResponse>> getWishList(@PathVariable long userId) {
        log.info("WishController.getWishList");
        return new BaseResponse<>(wishService.getWishList(userId));
    }

    /**
     * 찜
     */
    @PostMapping("/{storeId}")
    public BaseResponse<Object> addWishList(@PathVariable long userId, @PathVariable long storeId) {
        log.info("WishController.addWishList");
        wishService.addWishList(userId, storeId);
        return new BaseResponse<>(null);
    }

    /**
     * 찜 취소
     */
    @PatchMapping("/{storeId}")
    public BaseResponse<Object> modifyWishStatus_deleted(@PathVariable long userId, @PathVariable long storeId) {
        log.info("[WishController.modifyWishStatus_deleted]");
        wishService.modifyWishStatus_deleted(userId, storeId);
        return new BaseResponse<>(null);
    }
}
