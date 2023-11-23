package kuit2.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMenuResponse {
    private String name;
    private long price;
    private String image;
    private String category;
}
