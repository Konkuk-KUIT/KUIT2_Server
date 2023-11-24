package kuit2.server.service;

import kuit2.server.common.exception.DatabaseException;
import kuit2.server.common.exception.UserException;
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
@Service        // 서비스임을 명시
@RequiredArgsConstructor            // 의존관계주입
public class UserService {

    private final UserDao userDao;                      // service는 dao를 의존하고 있으므로 의존관계를 주입
    private final PasswordEncoder passwordEncoder;      // 의존관계주입(password 암호화)
    private final JwtProvider jwtProvider;

    // 실제 회원가입을 진행할 service의 signUp 메서드
    public PostUserResponse signUp(PostUserRequest postUserRequest) {
        log.info("[UserService.createUser]");

        // TODO: 1. validation (중복 검사)
        // 회원가입을 하려는 user가 중복된 email을 가지는지, 중복된 nickname을 가지는지를 확인해야함
        // -> DB조회를 통해야 가능함(왜 controller에서 validation을 안하고 service에서 하는가?)
        // => DB에 접근할 수 있는 계층인 DAO와 인접한 service에서 validation을 하는것이 더 타당함
        validateEmail(postUserRequest.getEmail());                  // 이메일 중복검사
        String nickname = postUserRequest.getNickname();
        if (nickname != null) {
            validateNickname(postUserRequest.getNickname());        // 닉네임 중복검사
        }

        // TODO: 2. password 암호화 -> 암호화하지 않고 DB에 저장하면 보안이슈 발생할 수 있음
        String encodedPassword = passwordEncoder.encode(postUserRequest.getPassword());         // 패스워드 암호화
        postUserRequest.resetPassword(encodedPassword);             // 암호화된 패스워드를 postUserRequest에 setting

        // TODO: 3. DB insert & userId 반환
        // 패스워드 암호화 후 DB에 insert & user ID 반환
        long userId = userDao.createUser(postUserRequest);

        // TODO: 4. JWT 토큰 생성
        String jwt = jwtProvider.createToken(postUserRequest.getEmail(), userId);

        return new PostUserResponse(userId, jwt);
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

    // userDao를 통해서 DB에 접근함으로써 validation 진행
    private void validateNickname(String nickname) {
        if (userDao.hasDuplicateNickName(nickname)) {
            throw new UserException(DUPLICATE_NICKNAME);
        }
    }
}