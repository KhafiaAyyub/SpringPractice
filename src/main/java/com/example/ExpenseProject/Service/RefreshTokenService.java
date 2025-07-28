package com.example.ExpenseProject.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ExpenseProject.Entities.RefreshToken;
import com.example.ExpenseProject.Entities.UserInfo;
import com.example.ExpenseProject.Repository.RefreshTokenRepository;
import com.example.ExpenseProject.Repository.UserRepository;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;  //singleton //instance bna liya

    @Autowired
    UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        UserInfo userInfoExtracted = userRepository.findbyUsername(username);
        RefreshToken refreshToken = RefreshToken.builder()
                    .userInfo(userInfoExtracted)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(600000))
                    .build();
        return refreshTokenRepository.save(refreshToken);  //by JPA by default
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

}