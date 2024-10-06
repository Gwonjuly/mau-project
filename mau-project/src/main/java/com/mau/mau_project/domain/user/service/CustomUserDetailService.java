package com.mau.mau_project.domain.user.service;

import com.mau.mau_project.db.user.entity.UserEntity;
import com.mau.mau_project.db.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with this user-name" + userName));
        return   new User(userEntity.getUserName(), userEntity.getPassword(), Collections.emptyList());
    }
}
