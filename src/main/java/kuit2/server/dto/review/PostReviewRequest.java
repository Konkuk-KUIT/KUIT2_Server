package kuit2.server.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostReviewRequest {
    private long orderId;
    private String reviewContent;
    private Double reviewScore;
}
