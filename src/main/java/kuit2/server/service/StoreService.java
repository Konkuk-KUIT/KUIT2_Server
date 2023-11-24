package kuit2.server.service;

import kuit2.server.common.exception.DatabaseException;
import kuit2.server.common.exception.StoreException;
import kuit2.server.dao.StoreDao;
import kuit2.server.dao.UserDao;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.dto.store.PostStoreRequest;
import kuit2.server.dto.store.PostStoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.DATABASE_ERROR;
import static kuit2.server.common.response.status.BaseExceptionResponseStatus.DUPLICATE_STORENAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreDao storeDao;

    public PostStoreResponse signUp(PostStoreRequest postStoreRequest) {
        validateStoreName(postStoreRequest.getStoreName());

        long storeId = storeDao.createStore(postStoreRequest);

        return new PostStoreResponse(storeId);
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




}

