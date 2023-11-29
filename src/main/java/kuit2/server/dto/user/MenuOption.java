package kuit2.server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuOption {

    private String menuOptionCategory;
    private String menuOptionName;
    private Integer menuOptionPrice;

}
