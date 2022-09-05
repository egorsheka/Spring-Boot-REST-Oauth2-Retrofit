package com.sandbox.sheka.controllers;

import java.io.IOException;
import com.sandbox.sheka.dto.payload.BeneficiaryDto;
import com.sandbox.sheka.dto.payload.CreditTransferTransactionDto;
import com.sandbox.sheka.dto.payload.CreditorAccountDto;
import com.sandbox.sheka.dto.payload.CreditorDto;
import com.sandbox.sheka.dto.payload.InstructedAmountDto;
import com.sandbox.sheka.dto.payload.PayloadDto;
import com.sandbox.sheka.dto.payload.PaymentTypeInformationDto;
import com.sandbox.sheka.dto.payload.RemittanceInformationDto;
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
    public String paymentRequests(OAuth2AuthenticationToken authentication) throws IOException
    {

        OAuth2AuthorizedClient client = authorizedClientService
            .loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());

        PayloadDto payload = PayloadDto.builder()
            .beneficiary(new BeneficiaryDto(new CreditorDto(), new CreditorAccountDto()))
            .creationDateTime("")
            .creditTransferTransaction(new CreditTransferTransactionDto(new InstructedAmountDto(), new RemittanceInformationDto(), "date"))
            .numberOfTransactions(1)
            .paymentInformationId("123435")
            .paymentTypeInformation(new PaymentTypeInformationDto())
            .build();

        return paymentFacade.pay(client, payload);
    }

}
