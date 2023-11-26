package kuit2.server.service;

import kuit2.server.dao.StoreDao;
import kuit2.server.dto.store.PostStoreRequest;
import kuit2.server.dto.store.PostStoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
