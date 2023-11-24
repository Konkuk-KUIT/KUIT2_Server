package kuit2.server.service;

import kuit2.server.dao.StoreDao;
import kuit2.server.dto.store.GetCategoryResponse;
import kuit2.server.dto.store.GetStoreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreDao storeDao;
    public List<GetCategoryResponse> getCategories() {
        log.info("[StoreService.getCategories]");
        return storeDao.getCategories();
    }

    public List<GetStoreResponse> getStoresByCategory(String category) {
        log.info("[StoreService.getStores]");
        return storeDao.getStoresByCategory(category);
    }

}
