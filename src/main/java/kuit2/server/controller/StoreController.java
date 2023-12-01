package kuit2.server.controller;

import kuit2.server.common.exception.StoreException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.dto.store.PostStoreRequest;
import kuit2.server.dto.store.PostStoreResponse;
import kuit2.server.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;
import static kuit2.server.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    /**
     * 가게 생성
     */
    @PostMapping(value ="/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
//    @PostMapping("/create")
    public BaseResponse<PostStoreResponse> createStore(@Validated @RequestBody PostStoreRequest postStoreRequest, BindingResult bindingResult) {
        log.info("[StoreController.createStore]");
        if (bindingResult.hasErrors()) {
            throw new StoreException(SERVER_ERROR, getErrorMessages(bindingResult));
        }

        return new BaseResponse<>(storeService.createStore(postStoreRequest));
    }

    /**
     * 가게 리스트 조회
     */
    @GetMapping("")
    public BaseResponse<List<GetStoreResponse>> getStores(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "active") String status) {
        log.info("[StoreController.getStores]");
        if (!status.equals("active") && !status.equals("dormant") && !status.equals("deleted")) {
            throw new StoreException(DATABASE_ERROR);
        }
        return new BaseResponse<>(storeService.getStoresByName(name, status));
    }

    @GetMapping("/search")
    public BaseResponse<List<GetStoreResponse>> getStoresByName(
            @RequestParam(defaultValue = "") String name,
            @RequestParam (required = false) String category,
            @RequestParam(required = false, defaultValue = "active") String status) {
        log.info("[StoreController.getStoresByName");
        if (!status.equals("active") && !status.equals("dormant") && !status.equals("deleted")) {
            throw new StoreException(DATABASE_ERROR);
        }

        if (name != null && !name.isEmpty()) {
            return new BaseResponse<>(storeService.getStoresByName(name, status));
        }

        if (category != null && !category.isEmpty()) {
            return new BaseResponse<>(storeService.getStoresByCategory(category, status));
        }

        throw new StoreException(DATABASE_ERROR);
    }

    @PatchMapping("/{storeId}/delete")
    public BaseResponse<Object> modifyStoreStatus_deleted(@PathVariable Long storeId){
        storeService.modifyStoreStatus_deleted(storeId);
        return new BaseResponse<>(null);
    }

}