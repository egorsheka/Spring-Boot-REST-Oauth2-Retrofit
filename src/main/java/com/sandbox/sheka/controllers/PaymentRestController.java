package com.sandbox.sheka.controllers;

import com.sandbox.sheka.dto.payload.PayloadDto;
import com.sandbox.sheka.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PaymentRestController
{
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final PaymentService paymentService;

    public PaymentRestController(OAuth2AuthorizedClientService authorizedClientService, PaymentService paymentService)
    {
        this.authorizedClientService = authorizedClientService;
        this.paymentService = paymentService;
    }

    @PostMapping("/payment-requests")
    public ResponseEntity<String> paymentRequests(OAuth2AuthenticationToken authentication, @RequestBody PayloadDto payload)
    {
        OAuth2AccessToken token = extractToken(authorizedClientService, authentication);

        return ResponseEntity.ok(paymentService.pay(token, payload));
    }

    public static OAuth2AccessToken extractToken(OAuth2AuthorizedClientService authorizedClientService, OAuth2AuthenticationToken authentication){
        return authorizedClientService.loadAuthorizedClient(
            authentication.getAuthorizedClientRegistrationId(),
            authentication.getName()).getAccessToken();
    }

}