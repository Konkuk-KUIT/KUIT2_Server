package kuit2.server.service;

import kuit2.server.common.exception.DatabaseException;
import kuit2.server.dao.WishDao;
import kuit2.server.dto.wish.GetWishListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.DATABASE_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishService {
    private final WishDao wishDao;

    public List<GetWishListResponse> getWishList(long userId) {
        log.info("WishService.getWishList");
        return wishDao.getWishList(userId);
    }

    public void addWishList(long userId, long storeId) {
        log.info("WishService.addWishList");
        int affectedRows = wishDao.modifyWishStatus_active(userId, storeId);
        if(affectedRows != 1) {
            wishDao.addWishList(userId, storeId);
        }
    }

    public void modifyWishStatus_deleted(long userId, long storeId) {
        log.info("[WishService.modifyWishStatus_deleted]");
        int affectedRows = wishDao.modifyWishStatus_deleted(userId, storeId);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }
}
