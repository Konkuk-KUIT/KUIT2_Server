package kuit2.server.service;

import java.util.List;
import kuit2.server.dao.ReviewDao;
import kuit2.server.dto.review.GetReviewResponse;
import kuit2.server.dto.review.PostReviewRequest;
import kuit2.server.dto.review.PostReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewDao reviewDao;

    public List<GetReviewResponse> getStoreReviews(long storeId) {
        log.info("[ReviewService.getStoreReviews]");

        return reviewDao.getStoreReviews(storeId);
    }

    public List<GetReviewResponse> getUserReviews(long userId) {
        log.info("[ReviewService.getUserReviews]");

        return reviewDao.getUserReviews(userId);
    }

    public PostReviewResponse writeReview(PostReviewRequest postReviewRequest) {
        log.info("[ReviewService.writeReview]");

        long reviewId = reviewDao.writeReview(postReviewRequest);
        return new PostReviewResponse(reviewId);
    }
}
