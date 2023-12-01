package kuit2.server.dto.restaurant;

import kuit2.server.dto.user.MenuOptionInCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMenuResponse {

    private long menuId;
    private String menuName;
    private String menuDescription;
    private float menuPrice;
    private List<MenuOptionInCategory> Options;
}
