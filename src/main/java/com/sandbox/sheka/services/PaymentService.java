package com.sandbox.sheka.services;


import com.sandbox.sheka.dto.payload.PayloadDto;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

public interface PaymentService
{
    String pay(OAuth2AccessToken token, PayloadDto payload);
}
