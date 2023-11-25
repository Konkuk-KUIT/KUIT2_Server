package kuit2.server.dto.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class PostUserAddressRequest {

    private long user_id;

    @NonNull
    @Length(max = 20, message = "분류: 최대 {max}자리까지 가능합니다")
    private String classification;

    @NotBlank(message = "email: {NotBlank}")
    @Length(max = 50, message = "도로명 주소: 최대 {max}자리까지 가능합니다")
    private String load_address;

    @Nullable
    @Length(max = 20, message = "세부 주소: 최대 {max}자리까지 가능합니다")
    private String detail_address;

    private Boolean is_now_address;

}
