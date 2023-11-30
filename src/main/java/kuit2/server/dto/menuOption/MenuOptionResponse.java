package kuit2.server.dto.menuOption;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuOptionResponse {
    private String category;
    private String name;
    private long additionalPrice;
}
