package kuit2.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {

    private String userId; // 확인 쉽게 하기 위해
    private String email;
    private String phoneNumber;
    private String nickname;
    private String profileImage;
    private String status;
}
