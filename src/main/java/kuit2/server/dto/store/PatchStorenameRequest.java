package kuit2.server.dto.store;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchStorenameRequest {

    @NotNull(message = "storename: {NotNull}")
    private String storename;
}
