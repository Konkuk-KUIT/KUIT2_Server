package kuit2.server.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRestaurantMenuResponse {

    private String menuCategory;
    private List<GetBriefMenuResponse> menus;

}
