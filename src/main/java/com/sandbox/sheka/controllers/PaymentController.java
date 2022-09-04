package com.sandbox.sheka.controllers;

import com.sandbox.sheka.facade.PaymentFacade;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController
{
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final PaymentFacade paymentFacade;

    public PaymentController(OAuth2AuthorizedClientService authorizedClientService, PaymentFacade paymentFacade)
    {
        this.authorizedClientService = authorizedClientService;
        this.paymentFacade = paymentFacade;
    }

    @GetMapping("/payment-requests")
    public String paymentRequests(OAuth2AuthenticationToken authentication){

        OAuth2AuthorizedClient client = authorizedClientService
            .loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());

        return paymentFacade.pay(client);
    }

}
