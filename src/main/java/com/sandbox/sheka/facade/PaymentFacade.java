package com.sandbox.sheka.facade;


import java.io.IOException;
import com.sandbox.sheka.dto.payload.PayloadDto;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

public interface PaymentFacade
{
    String pay(OAuth2AuthorizedClient client, PayloadDto payload) throws IOException;
}
