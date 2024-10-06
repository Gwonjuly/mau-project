package com.mau.mau_project.domain.user.controller;

import com.mau.mau_project.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final JwtUtil jwtUtil;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/validation")
    public void jwtValidate(@RequestParam(value = "token") String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token is not validation");
        }
    }
}
