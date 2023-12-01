package kuit2.server.controller;

import kuit2.server.common.exception.StoreException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.dto.store.PatchNameRequest;
import kuit2.server.dto.store.PostStoreRequest;
import kuit2.server.dto.store.PostStoreResponse;
import kuit2.server.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;
import static kuit2.server.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    /*
    * 가게 생성
    */
    @PostMapping("")
    BaseResponse<PostStoreResponse> createStore(@Validated @RequestBody PostStoreRequest postStoreRequest, BindingResult bindingResult) {
        log.info("[StoreController.createStore]");
        if(bindingResult.hasErrors()) {
            throw new StoreException(INVALID_STORE_VALUE);
        }
        return new BaseResponse<>(storeService.createStore(postStoreRequest));
    }

    /*
    * 가게 목록 조회
    * */
    @GetMapping("")
    public BaseResponse<List<GetStoreResponse>> getStores(
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(required = false, defaultValue = "0") String minDeliveryPrice,
            @RequestParam(required = false, defaultValue = "active") String status
    ) {
        log.info("[StoreController.getStores]");
        if(!status.equals("active")&&!status.equals("dormant")&&!status.equals("deleted")){
            throw new StoreException(INVALID_STORE_STATUS);
        }
        return new BaseResponse<>(storeService.getStores(category, status));
    }

    @PatchMapping("/{storeId}/name")
    public BaseResponse<String> modifyName(
            @PathVariable long storeId,
            @Validated @RequestBody PatchNameRequest patchNameRequest,
            BindingResult bindingResult
    ) {
        log.info("[StoreController.modifyName]");
        if (bindingResult.hasErrors()) {
            throw new StoreException(INVALID_STORE_VALUE, getErrorMessages(bindingResult));
        }
        storeService.modifyName(storeId, patchNameRequest.getName());
        return new BaseResponse<>(null);
    }

}
