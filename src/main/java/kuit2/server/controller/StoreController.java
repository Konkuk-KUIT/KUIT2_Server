package kuit2.server.controller;

import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.store.GetStoreRequest;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("")
    public BaseResponse<List<GetStoreResponse>> getStores(@Validated @RequestBody GetStoreRequest getStoreRequest,
                                                    BindingResult bindingResult) {
        log.info("[StoreController.getStores]");
        List<GetStoreResponse> stores = storeService.getStores(getStoreRequest.getName(), getStoreRequest.getCategory(),
                getStoreRequest.getMinDeliveryPrice(), getStoreRequest.getStatus());

        log.info(stores.toString());
        return new BaseResponse<>(stores);
    }
}
