package kuit2.server.util.jwt;

import io.jsonwebtoken.*;
import kuit2.server.common.exception.jwt.unauthorized.JwtInvalidTokenException;
import kuit2.server.common.exception.jwt.bad_request.JwtUnsupportedTokenException;
import kuit2.server.common.exception.jwt.unauthorized.JwtMalformedTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Component
public class JwtProvider {

    @Value("${secret.jwt-secret-key}")
    private String JWT_SECRET_KEY;

    @Value("${secret.jwt-expired-in}")
    private long JWT_EXPIRED_IN;

    public String createToken(String principal, long userId) { // principal : identifies the principal that is the subject of the JWT
        log.info("JWT key={}", JWT_SECRET_KEY);

        Claims claims = Jwts.claims().setSubject(principal); // Sets the JWT sub (subject) value
        Date now = new Date();
        Date validity = new Date(now.getTime() + JWT_EXPIRED_IN);

        return Jwts.builder()
                .setClaims(claims) // Set the JWT payload to be a JSON claims instance
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("userId", userId) // Set a custom JWT claims parameter value
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY) // Signs the constructed JWT using the specified algorithm with the specified key, producing a JWS
                .compact(); // Builds the JWT and serializes it to a compact, URL-safe string according to the JWT Compact Serialization
    }

    public boolean isExpiredToken(String token) throws JwtInvalidTokenException {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY).build()
                    .parseClaimsJws(token);
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

    // principal: 주체
    public String getPrincipal(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY).build() // 토큰을 생성할 때 사용했던 key를 set
                .parseClaimsJws(token) // 토큰을 Jws로 파싱
                .getBody().getSubject(); // 토큰에 저장했던 data들이 담긴 claims의 주체를 얻어옴
    }
}