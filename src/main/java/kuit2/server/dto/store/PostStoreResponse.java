package kuit2.server.dto.store;

import lombok.Getter;

@Getter
public class PostStoreResponse {
    private long storeId;

    public PostStoreResponse(long storeId) {
        this.storeId = storeId;
    }
}
