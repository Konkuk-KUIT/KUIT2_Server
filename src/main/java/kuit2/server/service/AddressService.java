package kuit2.server.service;

import kuit2.server.common.exception.AddressException;
import kuit2.server.dao.AddressDao;
import kuit2.server.dto.address.PostAddressRequest;
import kuit2.server.dto.address.PostAddressResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.DUPLICATE_CATEGORY;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressDao addressDao;

    public PostAddressResponse createAddress(PostAddressRequest postAddressRequest) {
        log.info("[AddressService.createAddress]");

        validCategory(postAddressRequest.getCategory());
        long addressId = addressDao.createAddress(postAddressRequest);

        return new PostAddressResponse(addressId);
    }

    private void validCategory(int category) {
        if(category == 0) return;

        if(addressDao.hasCategoryValue(category)) {
            throw new AddressException(DUPLICATE_CATEGORY);
        }
    }
}
