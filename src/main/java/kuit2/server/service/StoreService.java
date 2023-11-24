package kuit2.server.service;

import kuit2.server.common.exception.StoreException;
import kuit2.server.dao.StoreDao;
import kuit2.server.dao.UserDao;
import kuit2.server.dto.store.PostStoreRequest;
import kuit2.server.dto.store.PostStoreResponse;
import kuit2.server.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.DUPLICATE_NAME;

@Slf4j
@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreDao storeDao;

    public PostStoreResponse createStore(PostStoreRequest postStoreRequest) {
        log.info("[StoreService.createStore]");

        validateName(postStoreRequest.getName());

        long storeId = storeDao.createStore(postStoreRequest);

        return new PostStoreResponse(storeId);
    }

    private void validateName(String name) {
        if(storeDao.hasDuplicateName(name)) {
            throw new StoreException(DUPLICATE_NAME);
        }
    }
}
