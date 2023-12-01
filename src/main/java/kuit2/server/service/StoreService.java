package kuit2.server.service;

import kuit2.server.common.exception.DatabaseException;
import kuit2.server.dao.StoreDao;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.dto.store.PostStoreRequest;
import kuit2.server.dto.store.PostStoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.DATABASE_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreDao storeDao;

    public PostStoreResponse createStore(PostStoreRequest postStoreRequest) {
        log.info("[StoreService.createStore]");
        long storeId = storeDao.createStore(postStoreRequest);
        return new PostStoreResponse(storeId);
    }

    public List<GetStoreResponse> getStoresByName(String name, String status) {
        log.info("[StoreService.getStoresByName]");
        return storeDao.getStoresByName(name, status);
    }

    public List<GetStoreResponse> getStoresByCategory(String category, String status){
        log.info("[StoreService.getStoresByCategory]");
        return storeDao.getStoresByCategory(category, status);
    }

    public void modifyStoreStatus_deleted(long storeId) {
        log.info("StoreService.modifyStoreStatus_deleted]");

        int affectedRows = storeDao.modifyStoreStatus_deleted(storeId);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }
}
