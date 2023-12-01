package kuit2.server.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private long userId;
    private String accessToken;
    private String refreshToken;
}