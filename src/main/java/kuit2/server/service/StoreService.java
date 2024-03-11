package kuit2.server.service;

import kuit2.server.common.exception.DatabaseException;
import kuit2.server.common.exception.UserException;
import kuit2.server.dao.StoreDao;
import kuit2.server.dto.store.GetStoreResponse;
import kuit2.server.dto.user.GetUserResponse;
import kuit2.server.dto.user.PostUserRequest;
import kuit2.server.dto.user.PostUserResponse;
import kuit2.server.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreDao storeDao;
    private final JwtProvider jwtProvider;

    public List<GetStoreResponse> getStores(String storeName, String category, Integer minimumPrice, String address) {
        log.info("[StoreService.getStores]");
        return storeDao.getStores(storeName, category, minimumPrice, address);
    }
}