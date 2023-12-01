package kuit2.server.util;

import kuit2.server.dto.user.GetBriefRestaurantResponse;

import java.util.List;

public interface SortStrategy {
    List<GetBriefRestaurantResponse> sort(long lastId, long categoryId, String minOrderPrice);
}
