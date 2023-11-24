package kuit2.server.util.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kuit2.server.common.exception.jwt.unauthorized.JwtInvalidTokenException;
import kuit2.server.common.exception.jwt.bad_request.JwtUnsupportedTokenException;
import kuit2.server.common.exception.jwt.unauthorized.JwtMalformedTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static kuit2.server.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Component
public class JwtProvider {

    //@Value("${secret.jwt-secret-key}")
    private String JWT_SECRET_KEY = "JiaY9e2iDtCkvvmj3CoMqqby2746PTh5KIz23VxjA84=aldfeafsod";

    //@Value("${secret.jwt-expired-in}")
    private long JWT_EXPIRED_IN = 3600000;

    public String createToken(String principal, long userId) {
        log.info("JWT key={}", JWT_SECRET_KEY);

        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + JWT_EXPIRED_IN);

        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY);
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("userId", userId)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isExpiredToken(String token) throws JwtInvalidTokenException {

        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY);
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
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

    public String getPrincipal(String token) {

        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET_KEY);
        Key secretKey = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
