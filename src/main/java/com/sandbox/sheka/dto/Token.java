package com.sandbox.sheka.dto;

import lombok.Data;

@Data
public class Token
{
    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String refreshToken = null;
    private String scope;
}
