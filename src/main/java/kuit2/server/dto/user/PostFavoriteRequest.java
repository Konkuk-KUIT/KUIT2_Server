package kuit2.server.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostFavoriteRequest {
    @NotNull
    private long restaurantId;
}
