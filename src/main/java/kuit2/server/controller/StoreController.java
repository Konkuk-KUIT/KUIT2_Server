package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("")
    public BaseResponse<List<GetStoreResponse>> getStores(
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer minimumPrice,
            @RequestParam(required = false) String address) {
        log.info("[StoreController.getStores]");

        return new BaseResponse<>(storeService.getStores(storeName, category, minimumPrice, address));
    }
}
