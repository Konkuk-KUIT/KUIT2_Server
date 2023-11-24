package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.store.GetCategoryResponse;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    /**
     * 카테고리 목록 조회
     */
    @GetMapping("/categories")
    public BaseResponse<List<GetCategoryResponse>> getCategories() {
        log.info("[StoreController.getCategories]");
        return new BaseResponse<>(storeService.getCategories());
    }

    /**
    * 가게 목록 조회
     */
    @GetMapping("")
    public BaseResponse<List<GetStoreResponse>> getStoresByCategory(@RequestParam String category) {
        log.info("[StoreController.getStores]");
        return new BaseResponse<>(storeService.getStoresByCategory(category));
    }


}
