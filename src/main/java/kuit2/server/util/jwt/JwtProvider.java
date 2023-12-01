package kuit2.server.util.jwt;

import io.jsonwebtoken.*;
import kuit2.server.common.exception.jwt.unauthorized.JwtInvalidTokenException;
import kuit2.server.common.exception.jwt.bad_request.JwtUnsupportedTokenException;
import kuit2.server.common.exception.jwt.unauthorized.JwtMalformedTokenException;
import kuit2.server.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${secret.access-secret-key}")
    private String JWT_ACCESS_SECRET_KEY;

    @Value("${secret.refresh-secret-key}")
    private String JWT_REFRESH_SECRET_KEY;


    @Value("${secret.jwt-expired-in}")
    private long JWT_EXPIRED_IN;

    @Value("${secret.refresh-expired-in}")
    private long REFRESH_EXPIRED_IN;

    private final UserDao userDao;

    public Token createToken(String principal, long userId) {
        log.info("JWT key={}", JWT_ACCESS_SECRET_KEY);

        Claims claims = Jwts.claims().setSubject(principal);  //principal을 통해 payload의 subject 설정
        Date now = new Date();
        Date accessTokenValidity = new Date(now.getTime() + JWT_EXPIRED_IN);   //access 토근의 만료 시간 설정
        Date refreshTokenValidity = new Date(now.getTime() + REFRESH_EXPIRED_IN);   //refresh 토근의 만료 시간 설정

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(accessTokenValidity)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, JWT_ACCESS_SECRET_KEY)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(refreshTokenValidity)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, JWT_REFRESH_SECRET_KEY)
                .compact();

        userDao.updateRefreshToken(userId, refreshToken);

        return Token.builder().accessToken(accessToken).refreshToken(refreshToken).key(userId).build();
    }

    public boolean isAccessTokenExpired(String accessToken) throws JwtInvalidTokenException {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(JWT_ACCESS_SECRET_KEY).build()
                    .parseClaimsJws(accessToken);
            return claims.getBody().getExpiration().before(new Date());

        } catch (ExpiredJwtException e) {
            return true;

        } catch (UnsupportedJwtException e) {
            throw new JwtUnsupportedTokenException(UNSUPPORTED_TOKEN_TYPE);
        } catch (MalformedJwtException e) {
            throw new JwtMalformedTokenException(MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new JwtInvalidTokenException(INVALID_TOKEN);
        } catch (JwtException e) {
            log.error("[JwtTokenProvider.validateAccessToken]", e);
            throw e;
        }
    }

    public Token validateRefreshToken(String refreshToken, long userId) throws JwtInvalidTokenException {
        try {
            if (!userDao.isRefreshTokenValid(refreshToken, userId)) {
                throw new JwtInvalidTokenException(EXPIRED_REFRESH_TOKEN);
            }

            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(JWT_REFRESH_SECRET_KEY).build()
                    .parseClaimsJws(refreshToken);

            if (claims.getBody().getExpiration().before(new Date())) {
                throw new JwtInvalidTokenException(EXPIRED_REFRESH_TOKEN);
            }

            // Refresh 토큰이 유효하면 새로운 Access 토큰 생성
            String principal = getPrincipal(refreshToken);
            return createAccessToken(principal, userId);

        } catch (ExpiredJwtException e) {
            throw new JwtInvalidTokenException(EXPIRED_REFRESH_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new JwtUnsupportedTokenException(UNSUPPORTED_TOKEN_TYPE);
        } catch (MalformedJwtException e) {
            throw new JwtMalformedTokenException(MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new JwtInvalidTokenException(INVALID_TOKEN);
        } catch (JwtException e) {
            log.error("[JwtTokenProvider.validateRefreshToken]", e);
            throw e;
        }
    }

    private Token createAccessToken(String principal, long userId) {
        Date now = new Date();
        Date accessTokenValidity = new Date(now.getTime() + JWT_EXPIRED_IN);

        String accessToken = Jwts.builder()
                .setClaims(Jwts.claims().setSubject(principal))
                .setIssuedAt(now)
                .setExpiration(accessTokenValidity)
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS256, JWT_ACCESS_SECRET_KEY)
                .compact();

        // Refresh 토큰은 재발급하지 않음
        return Token.builder().accessToken(accessToken).key(userId).build();
    }

    public String getPrincipal(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_ACCESS_SECRET_KEY).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}