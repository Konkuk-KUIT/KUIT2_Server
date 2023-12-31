package kuit2.server.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JwtInfo {

    private String accessToken;
    private String refreshToken;
}
