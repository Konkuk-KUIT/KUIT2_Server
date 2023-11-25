package kuit2.server.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetDetailedStoreResponse {
    private String store_name;
    private String opening_time;    //String 말고 Time은?
    private String closing_time;
    private String store_address;
    private String store_number;
    private String store_description;

}
