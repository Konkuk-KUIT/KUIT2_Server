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
    private String storename;
    private String runningTime;
    private String address;
    private String profileImage;
    private String phoneNumber;
    private String status;

}
