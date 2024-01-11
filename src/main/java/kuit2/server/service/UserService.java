package kuit2.server.service;

import kuit2.server.common.exception.DatabaseException;
import kuit2.server.common.exception.UserException;
import kuit2.server.common.response.status.BaseExceptionResponseStatus;
import kuit2.server.dao.UserDao;
import kuit2.server.dto.auth.LoginRequest;
import kuit2.server.dto.user.*;
import kuit2.server.util.jwt.JwtInfo;
import kuit2.server.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
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
        JwtInfo jwtInfo = jwtProvider.createToken(postUserRequest.getEmail(), userId);

        return new PostUserResponse(userId, jwtInfo);
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
    public PostLoginResponse login(PostLoginRequest loginRequest) {
        Long userId;
        try {
            userId = userDao.getUserIdByEmail(loginRequest.getEmail());
        } catch (EmptyResultDataAccessException e) {
            throw new UserException(BaseExceptionResponseStatus.EMAIL_NOT_FOUND, "No user found with this email.");
        }

        String storedPassword = userDao.getPasswordByUserId(userId);
        if (!passwordEncoder.matches(loginRequest.getPassword(), storedPassword)) {
            throw new UserException(BaseExceptionResponseStatus.PASSWORD_NO_MATCH, "Password does not match.");
        }

        String jwt = jwtProvider.createToken(loginRequest.getEmail(), userId);
        return new PostLoginResponse(userId, jwt);
    }
}