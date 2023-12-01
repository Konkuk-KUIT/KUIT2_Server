package kuit2.server.dto.address;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostAddressRequest {

    private long userId;

    @NotBlank(message = "address: {NotBlank}")
    private String address;

    @Nullable
    private int category;
}
