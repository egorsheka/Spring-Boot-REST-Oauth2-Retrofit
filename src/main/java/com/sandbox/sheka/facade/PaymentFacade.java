package com.sandbox.sheka.facade;


import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

public interface PaymentFacade
{
    String pay(OAuth2AuthorizedClient client);
}
