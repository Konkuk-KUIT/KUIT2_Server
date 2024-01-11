package kuit2.server.dto.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetStoreRequest {
    private String name;
    private String category;
    private int minDeliveryPrice;
    private String status;
}
