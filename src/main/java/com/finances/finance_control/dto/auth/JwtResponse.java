package com.finances.finance_control.dto.auth;

import lombok.Getter;

@Getter
public class JwtResponse {
    private String tokenType;
    private String accessToken;
    private Long expiresIn;

    public JwtResponse(String tokenType, String accessToken, Long expiresIn) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
