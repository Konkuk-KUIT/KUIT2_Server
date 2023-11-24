package kuit2.server.dto.wish;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetWishListResponse {
    private String name;
    private double rating;
    private long minOrderPrice;
    private String image;
}
