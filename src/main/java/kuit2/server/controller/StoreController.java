package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.store.GetStoreInfoResponse;
import kuit2.server.dto.store.GetStoreListResponse;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    //식당 정보 보기
    @GetMapping("")
    public BaseResponse<List<GetStoreInfoResponse>> getStoreInfo(@PathVariable(name="store_id") Integer storeId)
    {
        log.info("[StoreController.getStoreInfo]");
        return new BaseResponse<>(storeService.getStoreInfo(storeId));
    }

    //식당 검색
    @GetMapping("/search")
    public BaseResponse<List<GetStoreResponse>> searchStore(@RequestParam("store_name") String storeName, @RequestParam(name="page") Integer page, @RequestParam("rows") Integer rows){
        log.info("[StoreController.searchStore]");
        return new BaseResponse<>(storeService.searchStore(storeName, page, rows));
    }

    //"양식"코너 식당 리스트
    @GetMapping("/list")
    public BaseResponse<List<GetStoreListResponse>> getStoreListByClassification(@RequestParam(name="classification") String classification, @RequestParam(name="page") Integer page, @RequestParam("rows") Integer rows){
        log.info("[StoreController.getStoreListByClassification]");

        return new BaseResponse<>(storeService.getStoreListByClassification(classification, page, rows));
    }

    //"분식"코너 최소주문금액 15000이하인 식당 리스트
//    @GetMapping("/list")
//    public BaseResponse<List<GetStoreListResponse>> getStoreListByMinPrice(@PathVariable(name="classification") String classification, @PathVariable(name = "min_order_price") Integer minOrderPrice){
//        log.info("[StoreController.getStoreListByMinPrice]");
//
//        return new BaseResponse<>(storeService.getStoreListByMinPrice(classification, minOrderPrice));
//    }
}
