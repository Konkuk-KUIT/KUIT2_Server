package kuit2.server.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetReviewResponse {

    private String storeName;
    private String reviewContent;
    private Double reviewScore;
    private String menuName;
}