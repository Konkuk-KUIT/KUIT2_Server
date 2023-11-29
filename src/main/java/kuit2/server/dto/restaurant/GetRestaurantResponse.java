package kuit2.server.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRestaurantResponse {
    private String name;
    private String address;
    private long minimumPrice;
    private String status;
}
