package kuit2.server.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetStoreResponse {
    private String name;
    private String phoneNumber;
    private String orderPlace;
    private long minOrderPrice;
    private String image;
    private String category;
    private double rating;
}
