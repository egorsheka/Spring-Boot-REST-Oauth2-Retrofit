package com.sandbox.sheka.controllers;

import java.io.IOException;
import com.sandbox.sheka.dto.PaymentPageDto;
import com.sandbox.sheka.dto.payload.BeneficiaryDto;
import com.sandbox.sheka.dto.payload.CreditTransferTransactionDto;
import com.sandbox.sheka.dto.payload.CreditorAccountDto;
import com.sandbox.sheka.dto.payload.CreditorDto;
import com.sandbox.sheka.dto.payload.InstructedAmountDto;
import com.sandbox.sheka.dto.payload.PayloadDto;
import com.sandbox.sheka.facade.PaymentFacade;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaymentController
{
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final PaymentFacade paymentFacade;

    public PaymentController(OAuth2AuthorizedClientService authorizedClientService, PaymentFacade paymentFacade)
    {
        this.authorizedClientService = authorizedClientService;
        this.paymentFacade = paymentFacade;
    }

    @PostMapping("/payment-requests")
    @ResponseBody
    public String paymentRequests(@ModelAttribute PaymentPageDto paymentPageDto, OAuth2AuthenticationToken authentication) throws IOException
    {
        OAuth2AuthorizedClient client = authorizedClientService
            .loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());
        //TODO validation
        PayloadDto payloadDto = PayloadDto.builder()
            .beneficiary(new BeneficiaryDto(new CreditorDto(paymentPageDto.getName()), new CreditorAccountDto(paymentPageDto.getIban())))
            .creditTransferTransaction(new CreditTransferTransactionDto(
                new InstructedAmountDto(Double.parseDouble(paymentPageDto.getAmount()), paymentPageDto.getCurrency()), null, null))
            .build();

        return paymentFacade.pay(client, payloadDto);
    }

    @GetMapping("/payment")
    public String greetingForm(Model model) {
        model.addAttribute("paymentPageDto", new PaymentPageDto());
        return "page";
    }

}
