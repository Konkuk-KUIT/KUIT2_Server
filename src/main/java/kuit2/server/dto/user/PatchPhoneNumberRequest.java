package kuit2.server.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchPhoneNumberRequest {

    @Pattern(regexp="\\d{3}-\\d{3,4}-\\d{4}",
            message="전화번호 형식에 맞지 않습니다. (예: 010-1234-5678)")
    @NotNull(message = "phoneNumber: {NotNull}")
    private String phoneNumber;

}