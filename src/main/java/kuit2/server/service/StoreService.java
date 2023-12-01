package kuit2.server.service;

import kuit2.server.common.exception.DatabaseException;
import kuit2.server.common.exception.StoreException;
import kuit2.server.dao.StoreDao;
import kuit2.server.dao.UserDao;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.dto.store.PostStoreRequest;
import kuit2.server.dto.store.PostStoreResponse;
import kuit2.server.util.Page;
import kuit2.server.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.DATABASE_ERROR;
import static kuit2.server.common.response.status.BaseExceptionResponseStatus.DUPLICATE_STORENAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreDao storeDao;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public PostStoreResponse signUp(PostStoreRequest postStoreRequest) {
        // TODO: 1. 가게 이름 중복 검사
        validateStoreName(postStoreRequest.getStoreName());

        // TODO: 2. password 암호화
        String encodedPassword = passwordEncoder.encode(postStoreRequest.getPassword());

        // TODO: 3. 암호화한 password DB insert & storeId 반환
        long storeId = storeDao.createStore(postStoreRequest);

        // TODO: 4. 가게 등록시 JWT 토큰 생성
        String jwt = jwtProvider.createToken(postStoreRequest.getStoreName(), storeId);
        return new PostStoreResponse(storeId, jwt);
    }

    private void validateStoreName(String storeName){
        if(storeDao.hasDuplicatedStoreName(storeName)){
            throw new StoreException(DUPLICATE_STORENAME);
        }
    }

    public void modifyStoreStatus_inactive(long storeId) {
        log.info("[StoreService.modifyStoreStatus_inactive]");

        int affectedRows = storeDao.modifyStoreStatus_inactive(storeId);
        if(affectedRows != 1){
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void modifyStorename(long storeId, String storename) {
        log.info("[StoreService.modifyStorename]");

        validateStoreName(storename);
        int affectedRows = storeDao.modifyStorename(storeId, storename);
        if(affectedRows != 1){
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public List<GetStoreResponse> getStores(String storename, String status) {
        log.info("[StoreService.getStores]");

        return storeDao.getStores(storename, status);
    }


    public List<GetStoreResponse> getStoresByPage(String storename, String status, long page) {
        log.info("[StoreService.getStoresByPage");

        return storeDao.getStoresByPage(storename, status, page);
    }
}

