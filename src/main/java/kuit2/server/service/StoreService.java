package kuit2.server.service;

import kuit2.server.dao.StoreDao;
import kuit2.server.dto.store.GetStoreInfoResponse;
import kuit2.server.dto.store.GetStoreListResponse;
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

    public List<GetStoreInfoResponse> getStoreInfo(Integer storeId){
        log.info("[StoreService.getStoreInfo]");
        return storeDao.getStoreInfo(storeId);
    }
    public List<GetStoreResponse> searchStore(String storeName){
        log.info("[StoreService.searchStore]");
        return storeDao.searchStore(storeName);
    }

    public List<GetStoreListResponse> getStoreListByClassification(String classification){
        log.info("[StoreService.getStoreListByClassification]");
        return storeDao.getStoreListByClassification(classification);
    }


    public List<GetStoreListResponse> getStoreListByMinPrice(String classification, Integer minOrderPrice) {
        log.info("[StoreService.getStoreListByMinPrice]");
        return storeDao.getStoreListByMinPrice(classification, minOrderPrice);
    }
}
