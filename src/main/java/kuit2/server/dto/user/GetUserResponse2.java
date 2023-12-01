package kuit2.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse2 {
    private List<GetUserResponse> userlist;
    private long lastId;
    private boolean hasNext;
}
