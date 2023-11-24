package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.storeCategory.GetStoreCategoriesResponse;
import kuit2.server.service.StoreCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/store-categories")
public class StoreCategoriesController {
    private final StoreCategoryService storeCategoryService;

    /**
     * 가게 카테고리 목록(ex 족발/보쌈, 회) 조회
     */
    /*
    @GetMapping("")
    public BaseResponse<List<GetStoreCategoriesResponse>> getStoreCategories(
            @RequestParam(required = false, defaultValue = "") String storeCategoryName
    ){

    }

     */
}
