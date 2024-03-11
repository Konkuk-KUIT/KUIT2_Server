package kuit2.server.dto.store;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
public class PostStoreRequest {

    @Nullable
    @Length(max = 25, message = "storeName: 최대 {max}자리까지 가능합니다")
    private String nickname;

}
