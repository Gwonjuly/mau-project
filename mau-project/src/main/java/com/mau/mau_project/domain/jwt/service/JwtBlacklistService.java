package com.mau.mau_project.domain.jwt.service;

import com.mau.mau_project.db.jwt.entity.JwtBlacklist;
import com.mau.mau_project.db.jwt.repository.JwtBlacklistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtBlacklistService {

    private final JwtBlacklistRepository jwtBlacklistRepository;
    private final JwtUtil jwtUtil;

    public void blacklistToken(String token, LocalDateTime expirationTime, String username) {
        JwtBlacklist jwtBlacklist = JwtBlacklist.builder()
                .token(token)
                .expirationTime(expirationTime)
                .username(username)
                .build();

        jwtBlacklistRepository.save(jwtBlacklist);
    }

    public boolean isTokenBlacklisted(String currentToken) {
        String username = jwtUtil.getUserNameFromToken(currentToken);
        Optional<JwtBlacklist> blacklistedToken = jwtBlacklistRepository.findTopByUsernameOrderByExpirationTime(username);
        if (blacklistedToken.isEmpty()) {
            return false;
        }
        Instant instant = jwtUtil.getExpirationDateFromToken(currentToken).toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return blacklistedToken.get().getExpirationTime().isAfter(localDateTime.minusMinutes(60));
    }
}
