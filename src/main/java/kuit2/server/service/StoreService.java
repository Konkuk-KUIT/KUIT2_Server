package kuit2.server.service;

import java.util.List;
import kuit2.server.dao.StoreDao;
import kuit2.server.dto.store.GetCategoryResponse;
import kuit2.server.dto.store.GetDetailedStoreResponse;
import kuit2.server.dto.store.GetStoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreDao storeDao;

    public List<GetStoreResponse> getStores(long categoryId) {
        log.info("[StoreService.getStores]");
        return storeDao.getStores(categoryId);
    }

    public GetDetailedStoreResponse getStore(long storeId) {
        log.info("[StoreService.getStore]");
        return storeDao.getStore(storeId);
    }

    public List<GetCategoryResponse> getCategories() {
        log.info("[StoreService.getCategories]");
        return storeDao.getCategories();
    }
}
