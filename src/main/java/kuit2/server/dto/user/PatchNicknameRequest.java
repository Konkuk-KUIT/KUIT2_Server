package kuit2.server.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class PatchNicknameRequest {

    @Length(max = 25, message = "nickname: 최대 {max}자리까지 가능합니다")
    @NotNull(message = "nickname: {NotNull}")
    private String nickname;

}