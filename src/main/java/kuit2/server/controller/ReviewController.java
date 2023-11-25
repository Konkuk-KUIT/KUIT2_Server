package kuit2.server.controller;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_REVIEW_VALUE;
import static kuit2.server.util.BindingResultUtils.getErrorMessages;

import java.util.List;
import kuit2.server.common.exception.ReviewException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.review.GetReviewResponse;
import kuit2.server.dto.review.PostReviewRequest;
import kuit2.server.dto.review.PostReviewResponse;
import kuit2.server.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 가게의 리뷰 리스트
     */
    @GetMapping("/{storeId}/reviews")
    public BaseResponse<List<GetReviewResponse>> getStoreReviews(
            @PathVariable("storeId") long storeId) {
        log.info("[ReviewController.getStoreReviews]");

        return new BaseResponse<>(reviewService.getStoreReviews(storeId));
    }

    /**
     * 유저의 리뷰 리스트
     */
    @GetMapping("/{userId}/reviews")
    public BaseResponse<List<GetReviewResponse>> getUserReviews(
            @PathVariable("userId") long userId) {
        log.info("[ReviewController.getUserReviews]");

        return new BaseResponse<>(reviewService.getUserReviews(userId));
    }

    /**
     * 해당 가게 리뷰 작성 주문id가 필요함
     */
    @PostMapping("")
    public BaseResponse<PostReviewResponse> writeReview(
            @RequestBody PostReviewRequest postReviewRequest, BindingResult bindingResult) {
        log.info("[ReviewController.writeReview]");
        if (bindingResult.hasErrors()) {
            throw new ReviewException(INVALID_REVIEW_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(reviewService.writeReview(postReviewRequest));
    }
}
