package kuit2.server.dto.auth;

import kuit2.server.util.jwt.JwtInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private long userId;
    private JwtInfo jwtInfo;

}