package com.dailycodework.buynowdotcom.controller;

import com.dailycodework.buynowdotcom.request.LoginRequest;
import com.dailycodework.buynowdotcom.security.jwt.JwtUtils;
import com.dailycodework.buynowdotcom.security.user.ShopUserDetailsService;
import com.dailycodework.buynowdotcom.utils.CookieUtils;
import com.twilio.rest.studio.v1.flow.engagement.Step;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;
    private final ShopUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    private Long refreshTokenExpirationTime;

    public ResponseEntity<?> authenticateUser(LoginRequest request, HttpServletResponse response){
        Authentication authentication =
                authenticationManager.authenticate((new UsernamePasswordAuthenticationToken
                        (request.getEmail(),request.getPassword())));
        String accessToken = jwtUtils.generateAccessTokenForUser(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(request.getEmail());
        cookieUtils.addRefreshTokenCookie(response,refreshToken,refreshTokenExpirationTime);
        Map<String, String> token = new HashMap<>();
        token.put("accessToken",accessToken);
        return  ResponseEntity.ok(token);
    }

    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request){
        cookieUtils.logCookie();
    }

}
