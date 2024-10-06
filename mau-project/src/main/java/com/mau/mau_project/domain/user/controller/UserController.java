package com.mau.mau_project.domain.user.controller;

import com.mau.mau_project.domain.user.controller.model.UserLoginRequest;
import com.mau.mau_project.db.user.entity.UserEntity;
import com.mau.mau_project.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")

public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserLoginRequest request){
        UserEntity userEntity = userService.createUser(request);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "delete user ID", required = true) @PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
