package kuit2.server.dto.coupon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class PostCouponRequest {
    @NotBlank(message = "code: {NotBlank}")
    @Length(min = 4, max = 10, message = "code: 최소 {min}자리, 최대 {max}자리까지 가능합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{4,10}$",
            message = "password: 대문자, 숫자가 적어도 하나씩은 있어야 합니다")
    private String code;

    @NotNull
    private String name;

    @NotNull
    private long discountPrice;

    @NotNull
    private long minOrderPrice;

    @NotNull
    private long deadline;

    @NotNull
    private long userId;
}
