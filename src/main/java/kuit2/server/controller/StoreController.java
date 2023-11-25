package kuit2.server.controller;

import java.util.List;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.store.GetCategoryResponse;
import kuit2.server.dto.store.GetDetailedStoreResponse;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    /**
     * 모든 카테고리들
     */
    @GetMapping("/categories")
    public BaseResponse<List<GetCategoryResponse>> getCategories() {
        log.info("StoreController.getCategories");

        return new BaseResponse<>(storeService.getCategories());
    }

    /**
     * 해당 카테고리에 해당하는 가게 리스트
     */
    @GetMapping("/")
    public BaseResponse<List<GetStoreResponse>> getStores(@RequestParam long categoryId) {
        log.info("[StoreController.getStores/categoryId={}", categoryId);

        return new BaseResponse<>(storeService.getStores(categoryId));
    }

    /**
     * 가게 상세 정보
     */
    @GetMapping("/{id}")
    public BaseResponse<GetDetailedStoreResponse> getStore(@PathVariable("id") long storeId) {
        log.info("[StoreController.getStore/{id}", storeId);

        return new BaseResponse<>(storeService.getStore(storeId));
    }


}
