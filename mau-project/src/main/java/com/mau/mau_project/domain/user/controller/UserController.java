package com.mau.mau_project.domain.user.controller;

import com.mau.mau_project.domain.jwt.service.JwtBlacklistService;
import com.mau.mau_project.domain.user.controller.model.UserLoginRequest;
import com.mau.mau_project.domain.user.controller.model.UserSignUpRequest;
import com.mau.mau_project.db.user.entity.UserEntity;
import com.mau.mau_project.domain.user.service.CustomUserDetailService;
import com.mau.mau_project.domain.user.service.UserService;
import com.mau.mau_project.domain.jwt.service.JwtUtil;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;
    private final JwtBlacklistService jwtBlacklistService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserSignUpRequest request){
        UserEntity userEntity = userService.createUser(request);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "delete user ID", required = true) @PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest request, HttpServletResponse response) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        UserDetails userDetails = customUserDetailService.loadUserByUsername(request.getUserName());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return token;
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response, HttpServletRequest request, @CookieValue(value = "token", required = false) String cookieToken, @RequestParam(value = "requestToken", required = false) String requestToken){
        String token = null;
        String bearerToken = request.getHeader("Authorization");
        if (requestToken != null) {
            token = requestToken;
        } else if (cookieToken != null) {
            token = cookieToken;
        } else if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }
        Instant instant = new Date().toInstant();
        LocalDateTime expirationTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        String userName = jwtUtil.getUserNameFromToken(token);
        jwtBlacklistService.blacklistToken(token, expirationTime, userName);

        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
