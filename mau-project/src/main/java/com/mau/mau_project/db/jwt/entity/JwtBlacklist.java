package com.mau.mau_project.db.jwt.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expirationTime;

    @Column(nullable = false)
    private String username;
}
