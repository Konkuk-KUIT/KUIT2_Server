package kuit2.server.dto.restaurant;

import kuit2.server.dto.user.GetBriefRestaurantResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRestaurantResponse {
    private boolean hasNext;
    private List<GetBriefRestaurantResponse> restaurants;
}
