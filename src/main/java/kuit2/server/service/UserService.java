package kuit2.server.service;

import kuit2.server.common.exception.DatabaseException;
import kuit2.server.common.exception.UserException;
import kuit2.server.common.response.BaseResponse;
import kuit2.server.dao.OrderMenuDao;
import kuit2.server.dao.RestaurantDao;
import kuit2.server.dao.ReviewDao;
import kuit2.server.dao.UserDao;
import kuit2.server.dto.user.*;
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
public class UserService {

    private final UserDao userDao;
    private final OrderMenuDao orderMenuDao;
    private final ReviewDao reviewDao;
    private final RestaurantDao restaurantDao;

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public PostUserResponse signUp(PostUserRequest postUserRequest) {
        log.info("[UserService.createUser]");

        // TODO: 1. validation (중복 검사)
        validateEmail(postUserRequest.getEmail());
        String nickname = postUserRequest.getNickname();
        if (nickname != null) {
            validateNickname(postUserRequest.getNickname());
        }

        // TODO: 2. password 암호화
        String encodedPassword = passwordEncoder.encode(postUserRequest.getPassword());
        postUserRequest.resetPassword(encodedPassword);

        // TODO: 3. DB insert & userId 반환
        long userId = userDao.createUser(postUserRequest);

        // TODO: 4. JWT 토큰 생성
//        String jwt = jwtProvider.createToken(postUserRequest.getEmail(), userId);
        String jwt = null;

        return new PostUserResponse(userId, jwt);
    }

    public PostLoginResponse login(PostLoginRequest postLoginRequest) {
        log.info("[UserService.login]");

        Long logginedUserId = userDao.getUserIdByEmail(postLoginRequest.getEmail());
        if(logginedUserId != null && passwordEncoder.matches(postLoginRequest.getPassword(), userDao.getPasswordByUserId(logginedUserId))){
            return new PostLoginResponse(logginedUserId, null);
        }

        throw new UserException(USER_NOT_FOUND);
    }

    public GetUserResponse getUserInfo(Long userId) {
        log.info("[UserService.getUserInfo");

        GetUserResponse getUserResponse = userDao.getUserByUserId(userId);
        if (getUserResponse != null) {
            return getUserResponse;
        }

        throw new UserException(USER_NOT_FOUND);

    }

    public void modifyUserStatus_dormant(long userId) {
        log.info("[UserService.modifyUserStatus_dormant]");

        int affectedRows = userDao.modifyUserStatus_dormant(userId);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserStatus_deleted(long userId) {
        log.info("[UserService.modifyUserStatus_deleted]");

        int affectedRows = userDao.modifyUserStatus_deleted(userId);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void modifyNickname(long userId, String nickname) {
        log.info("[UserService.modifyNickname]");

        validateNickname(nickname);
        int affectedRows = userDao.modifyNickname(userId, nickname);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserResponse> getUsers(String nickname, String email, String status) {
        log.info("[UserService.getUsers]");
        return userDao.getUsers(nickname, email, status);
    }

    private void validateEmail(String email) {
        if (userDao.hasDuplicateEmail(email)) {
            throw new UserException(DUPLICATE_EMAIL);
        }
    }

    private void validateNickname(String nickname) {
        if (userDao.hasDuplicateNickName(nickname)) {
            throw new UserException(DUPLICATE_NICKNAME);
        }
    }

    public List<GetCartResponse> getCart(long userId) {
        log.info("[UserService.getUsers]");

        if (userDao.getUserByUserId(userId) != null) {
            return orderMenuDao.getOrderMenusByUserId(userId);
        }

        throw new UserException(USER_NOT_FOUND);
    }

    public List<GetReviewResponse> getReviews(long userId) {
        log.info("[UserService.getReviews]");

        if (userDao.getUserByUserId(userId) != null) {
            return reviewDao.getReviewsByUserId(userId);
        }

        throw new UserException(USER_NOT_FOUND);

    }

    public String addFavorite(long userId, long restaurantId) {
        log.info("[UserService.addFavorite]");

        if (userDao.getUserByUserId(userId) != null && restaurantDao.getBriefRestaurantById(restaurantId) != null) {
            return userDao.addFavorite(userId, restaurantId);
        }

        throw new UserException(USER_NOT_FOUND);
    }

    public String deleteFavorite(long userId, long restaurantId) {
        log.info("[UserService.deleteFavorite]");

        if (userDao.getUserByUserId(userId) != null && restaurantDao.getBriefRestaurantById(restaurantId) != null) {
            return userDao.deleteFavorite(userId, restaurantId);
        }

        throw new UserException(USER_NOT_FOUND);

    }

    public GetFavoriteResponse getFavorites(long userId) {
        log.info("[UserService.getFavorite]");

        if (userDao.getUserByUserId(userId) != null) {
            return userDao.getFavorite(userId);
        }

        throw new UserException(USER_NOT_FOUND);
    }
}