package kuit2.server.service;

import kuit2.server.common.exception.DatabaseException;
import kuit2.server.common.exception.StoreException;
import kuit2.server.dao.StoreDao;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.dto.store.PostStoreRequest;
import kuit2.server.dto.store.PostStoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.DATABASE_ERROR;
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

    public List<GetStoreResponse> getStores(String category, String status, int pageNo) {
        log.info("[StoreService.getStores]");
        return storeDao.getStores(category, status, pageNo);
    }

    public void modifyName(long storeId, String name) {
        log.info("[StoreService.modifyName]");

        int affectedRows = storeDao.modifyName(storeId, name);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    private void validateName(String name) {
        if(storeDao.hasDuplicateName(name)) {
            throw new StoreException(DUPLICATE_NAME);
        }
    }
}
