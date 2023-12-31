package kuit2.server.dto.user;

import kuit2.server.util.jwt.JwtInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUserResponse {

    private long userId;
    private JwtInfo jwtInfo;
}