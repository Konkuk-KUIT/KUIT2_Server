package kuit2.server.service;

import kuit2.server.common.exception.UserException;
import kuit2.server.common.exception.jwt.unauthorized.JwtUnauthorizedTokenException;
import kuit2.server.dao.UserDao;
import kuit2.server.dto.auth.LoginRequest;
import kuit2.server.dto.auth.LoginResponse;
import kuit2.server.util.jwt.JwtInfo;
import kuit2.server.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public LoginResponse login(LoginRequest authRequest) {
        log.info("[AuthService.login]");

        String email = authRequest.getEmail();

        // TODO: 1. 이메일 유효성 확인
        long userId;
        try {
            userId = userDao.getUserIdByEmail(email);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UserException(EMAIL_NOT_FOUND);
        }

        // TODO: 2. 비밀번호 일치 확인
        validatePassword(authRequest.getPassword(), userId);

        // TODO: 3. JWT 갱신
        JwtInfo updatedJwtInfo = jwtProvider.createToken(email, userId);

        return new LoginResponse(userId, updatedJwtInfo);
    }

    private void validatePassword(String password, long userId) {
        String encodedPassword = userDao.getPasswordByUserId(userId);
        System.out.println("password = " + password);
        System.out.println("encodedPassword = " + encodedPassword);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new UserException(PASSWORD_NO_MATCH);
        }
    }

    public long getUserIdByEmail(String email) {
        try {
            return userDao.getUserIdByEmail(email);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new JwtUnauthorizedTokenException(TOKEN_MISMATCH);
        }
    }

}
