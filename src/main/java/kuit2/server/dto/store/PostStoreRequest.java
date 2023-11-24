package kuit2.server.dto.store;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
}
