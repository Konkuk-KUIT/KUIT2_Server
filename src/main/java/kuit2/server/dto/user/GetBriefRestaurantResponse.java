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
public class GetBriefRestaurantResponse {

    private String restaurantName;
    private int star_count;
    private int review_count;
    private List<String> representMenu;
    private String eta;
    private float minOrderPrice;

}
