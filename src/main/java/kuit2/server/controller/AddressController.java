package kuit2.server.controller;

import kuit2.server.common.exception.AddressException;
import kuit2.server.common.exception.UserException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dto.address.PostAddressRequest;
import kuit2.server.dto.address.PostAddressResponse;
import kuit2.server.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_ADDRESS_VALUE;
import static kuit2.server.common.response.status.BaseExceptionResponseStatus.INVALID_USER_VALUE;
import static kuit2.server.util.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("")
    public BaseResponse<PostAddressResponse> createAddress(
            @Validated @RequestBody PostAddressRequest postAddressRequest,
            BindingResult bindingResult
    ) {
        log.info("[AddressController.createAddress]");
        if (bindingResult.hasErrors()) {
            throw new AddressException(INVALID_ADDRESS_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(addressService.createAddress(postAddressRequest));
    }

}
