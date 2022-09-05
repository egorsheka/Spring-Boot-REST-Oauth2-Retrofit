package com.sandbox.sheka.controllers;

import java.io.IOException;
import com.sandbox.sheka.dto.payload.PayloadDto;
import com.sandbox.sheka.facade.PaymentFacade;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PaymentRestController
{
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final PaymentFacade paymentFacade;

    public PaymentRestController(OAuth2AuthorizedClientService authorizedClientService, PaymentFacade paymentFacade)
    {
        this.authorizedClientService = authorizedClientService;
        this.paymentFacade = paymentFacade;
    }

    @PostMapping("/payment-requests")
    public String paymentRequests(OAuth2AuthenticationToken authentication, @RequestBody PayloadDto payload) throws IOException
    {
        OAuth2AuthorizedClient client = authorizedClientService
            .loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());

        return paymentFacade.pay(client, payload);
    }

}