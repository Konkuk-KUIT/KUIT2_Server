package kuit2.server.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBriefMenuResponse {
    private long menuId;
    private String name;
    private String description;
    private float price;
    private String category;

}
