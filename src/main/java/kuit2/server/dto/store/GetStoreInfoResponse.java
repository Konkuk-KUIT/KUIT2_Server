package kuit2.server.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetStoreInfoResponse {

    private String storeName;
    private String address;
    private Integer minOrderPrice;
    private Double star;
    private String menu;
}
