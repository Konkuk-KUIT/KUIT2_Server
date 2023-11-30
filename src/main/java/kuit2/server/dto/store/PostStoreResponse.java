package kuit2.server.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostStoreResponse {
    private long storeId;
    private String jwt;

    public PostStoreResponse(long storeId) {
        this.storeId = storeId;
    }
}
