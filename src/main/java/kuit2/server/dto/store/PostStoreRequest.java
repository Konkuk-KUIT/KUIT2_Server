package kuit2.server.dto.store;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class PostStoreRequest {

    @NotBlank(message = "name:{NotBlank}")
    @Length(max = 50, message = "가게명: 최대 {max}자리까지 가능합니다.")
    private String name;

    @NotBlank(message = "category: {NotBlank}")
    private String category;

    @NotBlank(message = "address: {NotBlank}")
    private String address;

    @NotBlank(message = "phone: {NotBlank}")
    @Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}",
            message = "전화번호 형식에 맞지 않습니다. (예: 010-1234-5678)")
    @Length(max = 20, message = "phoneNumber: 최대 {max}자리까지 가능합니다")
    private String phone;

    @Nullable
    private String content;


}
