package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.review.PostReviewRequest;
import kuit2.server.dto.review.PostReviewResponse;
import kuit2.server.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 리뷰 등록
     */
    /*
    @PostMapping("")
    public BaseResponse<PostReviewResponse> signUp(@Validated @RequestBody PostReviewRequest postReviewRequest, BindingResult bindingResult){
        log.info("[ReviewController.signUp]");


        if(bindingResult.hasErrors()){
            throw new ReviewException(IN)
        }

    }

    */
}
