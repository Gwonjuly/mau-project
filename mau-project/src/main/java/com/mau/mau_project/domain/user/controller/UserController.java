package com.mau.mau_project.domain.user.controller;

import com.mau.mau_project.domain.user.controller.model.UserLoginRequest;
import com.mau.mau_project.domain.user.controller.model.UserSignUpRequest;
import com.mau.mau_project.db.user.entity.UserEntity;
import com.mau.mau_project.domain.user.service.CustomUserDetailService;
import com.mau.mau_project.domain.user.service.UserService;
import com.mau.mau_project.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtil jwtUtil;

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
    public String login(@RequestBody UserLoginRequest request) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        UserDetails userDetails = customUserDetailService.loadUserByUsername(request.getUserName());
        return jwtUtil.generateToken(userDetails.getUsername());
    }
}
