package kuit2.server.controller;

import kuit2.server.common.exception.StoreException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.common.response.status.ResponseStatus;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.dto.store.PatchStorenameRequest;
import kuit2.server.dto.store.PostStoreRequest;
import kuit2.server.dto.store.PostStoreResponse;
import kuit2.server.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_STORE_STATUS;
import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_STORE_VALUE;
import static kuit2.server.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;
//    private final PostStoreRequestValidator postStoreRequestValidator;

//    @InitBinder         // 로컬 검증기
//    public void init(WebDataBinder dataBinder){
//        dataBinder.addValidators(postStoreRequestValidator);            // 컨트롤러에 postStoreRequestValidator가 등록됨
//    }


    /**
     * 가게 등록
     */
    @PostMapping("")
    public BaseResponse<PostStoreResponse> signUp(@Validated @RequestBody PostStoreRequest postStoreRequest, BindingResult bindingResult){
//        postStoreRequestValidator.validate(postStoreRequest, bindingResult);            // controller에서 검증기 직접 호출
        log.info("[StoreController.singUp]");

        if(bindingResult.hasErrors()){
            throw new StoreException(INVALID_STORE_VALUE, getErrorMessages(bindingResult));
        }

        PostStoreResponse postStoreResponse = storeService.signUp(postStoreRequest);
        return new BaseResponse<>(postStoreResponse);
    }


    /**
     * 가게 휴먼 전환
     */
    @PatchMapping("/{storeId}/inactive")
    public BaseResponse<Object> modifyStoreStatus_inactive(@PathVariable long storeId){
        log.info("[StoreController.modifyStoreStatus_inactive]");
        storeService.modifyStoreStatus_inactive(storeId);
        return new BaseResponse<>(null);
    }

    /**
     * 가게 이름 변경
     */
    @PatchMapping("/{storeId}/storename")
    public BaseResponse<String> modifyStorename(@PathVariable long storeId, @Validated @RequestBody PatchStorenameRequest patchStorenameRequest, BindingResult bindingResult){
        log.info("[StoreController.modifyStorename}");

        if(bindingResult.hasErrors()){
            throw new StoreException(INVALID_STORE_VALUE, getErrorMessages(bindingResult));
        }

        // 바꿀 이름을 patchStorenameRequest.getStorename() 으로 찾기
        storeService.modifyStorename(storeId, patchStorenameRequest.getStorename());
        return new BaseResponse<>(null);
    }

    /**
     * 가게 조회 -> 가게 이름으로 조회
     */
    @GetMapping("")
    public BaseResponse<List<GetStoreResponse>> getStores(
       @RequestParam(required = false, defaultValue = "") String storename,
       @RequestParam(required = false, defaultValue = "active") String status){

        log.info("[StoreController.getStores");

        if(!status.equals("active") && !status.equals("inactive")){
            throw new StoreException(INVALID_STORE_STATUS);
        }
        return new BaseResponse<>(storeService.getStores(storename, status));
    }


}
