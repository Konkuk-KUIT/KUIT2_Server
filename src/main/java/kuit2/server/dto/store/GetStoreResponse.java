package kuit2.server.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetStoreResponse {

    private String name;
    private String category;
    private String address;
    private String phone;
    private int minDeliveryPrice;
    private String status;

}
