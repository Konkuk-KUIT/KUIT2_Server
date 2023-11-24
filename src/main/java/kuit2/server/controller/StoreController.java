package kuit2.server.controller;

import kuit2.server.common.exception.StoreException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.store.PostStoreRequest;
import kuit2.server.dto.store.PostStoreResponse;
import kuit2.server.service.StoreService;
import kuit2.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_STORE_VALUE;

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
}
