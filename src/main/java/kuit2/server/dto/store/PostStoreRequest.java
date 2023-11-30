package kuit2.server.dto.store;

import jakarta.annotation.Nullable;
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
public class PostStoreRequest {

    @NotBlank(message = "address : {NotBlank}")
    @Length(max = 100, message = "address : 최대 {max}자리까지 가능합니다.")
    private String address;

    @NotBlank(message = "phoneNumber : {NotBlank}")
    @Length(max = 20, message = "phoneNumber : 최대 {max}자리까지 가능합니다.")
    private String phoneNumber;

    @NotBlank(message = "runningTime : {NotBlank}")
    @Length(max = 100, message = "runningTime : 최대 {max}자리까지 가능합니다.")
    private String runningTime;

    @NotBlank(message = "storeName : {NotBlank}")
    @Length(max = 100, message = "storeName : 최대 {max}자리까지 가능합니다.")
    private String storeName;

    @Nullable
    private String profileImage;

    @NotBlank(message = "password: {NotBlank}")
    @Length(min = 8, max = 20, message = "password: 최소 {min}자리 ~ 최대 {max}자리까지 가능합니다")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}", message = "password: 대문자, 소문자, 특수문자가 적어도 하나씩은 있어야 합니다")
    private String password;


}
