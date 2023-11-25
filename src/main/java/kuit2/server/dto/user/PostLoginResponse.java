package kuit2.server.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLoginResponse {

    @NotNull
    private long userId;
    private String jwt;
}
