package com.mau.mau_project.domain.user.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequest {

    private String userName;
    private String password;
    private String email;
}
