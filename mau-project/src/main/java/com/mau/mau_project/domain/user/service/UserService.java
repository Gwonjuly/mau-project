package com.mau.mau_project.domain.user.service;

import com.mau.mau_project.db.user.entity.UserEntity;
import com.mau.mau_project.db.user.repository.UserRepository;
import com.mau.mau_project.domain.user.controller.model.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity createUser(UserLoginRequest request){
        UserEntity userEntity = UserEntity.builder()
                .userName(request.getUserName())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
