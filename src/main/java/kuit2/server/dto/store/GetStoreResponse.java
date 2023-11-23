package kuit2.server.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetStoreResponse {
    private String name;
    private String phone_number;
    private String order_place;
    private long min_order_price;
    private String image;
    private String category;
    private double rating;
}
