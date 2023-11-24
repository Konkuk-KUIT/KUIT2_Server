package kuit2.server.dto.user;

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
@NoArgsConstructor          // 해당 클래스에 대한 빈껍데기를 만들어줌 -> 생성된 빈껍데기에 objectMapper를 통해서 값을 주입해줌(@Setter 어노테이션 필요!!)
public class PostUserRequest {
    // 회원가입시 필요한 정보 -> email, password, phonenumber, nickname, profileImage

    @Email(message = "email: 이메일 형식이어야 합니다")
    @NotBlank(message = "email: {NotBlank}")           // errors.properties에서 동적으로 읽을 수 있음
    @Length(max = 50, message = "email: 최대 {max}자리까지 가능합니다")
    private String email;

    @NotBlank(message = "password: {NotBlank}")
    @Length(min = 8, max = 20,
            message = "password: 최소 {min}자리 ~ 최대 {max}자리까지 가능합니다")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}",
            message = "password: 대문자, 소문자, 특수문자가 적어도 하나씩은 있어야 합니다")
    private String password;

    @NotBlank(message = "phoneNumber: {NotBlank}")
    @Length(max = 20, message = "phoneNumber: 최대 {max}자리까지 가능합니다")
    private String phoneNumber;

    @Nullable
    @Length(max = 25, message = "nickname: 최대 {max}자리까지 가능합니다")
    private String nickname;

    @Nullable
    private String profileImage;

    public void resetPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
