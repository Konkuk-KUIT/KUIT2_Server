package kuit2.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUserResponse {
    private long userId;
    private String jwt;         // jwt : 10주차에서 자세히배움(로그인시에 필요한 어떤게 있다!!)
}