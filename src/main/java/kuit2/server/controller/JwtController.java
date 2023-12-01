package kuit2.server.controller;

import kuit2.server.common.exception.jwt.unauthorized.JwtInvalidTokenException;
import kuit2.server.util.jwt.JwtProvider;
import kuit2.server.util.jwt.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JwtController {
    private final JwtProvider jwtProvider;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken, @RequestParam long userId) {
        try {
            Token newToken = jwtProvider.validateRefreshToken(refreshToken, userId);
            return ResponseEntity.ok(newToken);
        } catch (JwtInvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }
}
