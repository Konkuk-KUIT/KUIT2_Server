package kuit2.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUserResponse {

    private long userId;
    private String jwt = null;
}