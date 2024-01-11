package kuit2.server.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetStoreListResponse {
    private List<GetStoreResponse> userResponseList;
    private boolean hasNext;
    private long lastId;
}
