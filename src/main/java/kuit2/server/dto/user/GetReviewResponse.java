package kuit2.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewResponse {

    private Long reviewId;
    private String restaurantName;
    private int starCount;
    private String bodyText;
    private String bossComment;
    private List<ReviewImage> reviewImages;
    private List<ReviewMenu> reviewMenus;
}

