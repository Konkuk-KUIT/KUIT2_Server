package kuit2.server.service;

import kuit2.server.dao.StoreDao;
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
    
    public List<GetStoreResponse> getStores(String name, String category, int minDeliveryPrice, String status) {
        log.info("[StoreService.getUsers]");
        return storeDao.getStores(name, category, minDeliveryPrice, status);
    }
}
