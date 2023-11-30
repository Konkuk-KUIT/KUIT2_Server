package kuit2.server.dto.store;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class PostStoreRequest {

    @NotBlank(message = "name: {NotBlank}")
    private String name;

    @NotBlank(message = "address: {NotBlank}")
    private String address;

    @NotBlank(message = "phoneNumber: {NotBlank}")
    @Length(max = 20, message = "phoneNumber: 최대 {max}자리까지 가능합니다")
    private String phoneNumber;

    @Nullable
    private String category;

    @Nullable
    @Min(value = 0, message = "minDeliveryPrice : {value}이상의 값을 입력해 주세요")
    private int minDeliveryPrice;
}
