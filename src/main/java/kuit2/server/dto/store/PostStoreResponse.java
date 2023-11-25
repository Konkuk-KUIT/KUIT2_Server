package kuit2.server.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostStoreResponse {

    private long storeId;
    private String jwt;
}
