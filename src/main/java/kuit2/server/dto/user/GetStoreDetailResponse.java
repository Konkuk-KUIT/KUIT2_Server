package kuit2.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetStoreDetailResponse {
    private String storeName;

    private String storeImage;

    private String address;

    private String storeHours;

    private String closedDays;

    private int numOfLike;

    private int type;

    private BigDecimal rating;

    private String phone;
}
