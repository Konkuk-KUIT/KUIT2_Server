package kuit2.server.dto.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponsePage {

    private List<GetUserResponse> userResponseList;
    private boolean hasNext;
    private long lastId;

}
