package com.sandbox.sheka.controllers;

import javax.servlet.http.HttpSession;
import com.sandbox.sheka.dto.PaymentPageDto;
import com.sandbox.sheka.dto.payload.BeneficiaryDto;
import com.sandbox.sheka.dto.payload.CreditTransferTransactionDto;
import com.sandbox.sheka.dto.payload.CreditorAccountDto;
import com.sandbox.sheka.dto.payload.CreditorDto;
import com.sandbox.sheka.dto.payload.InstructedAmountDto;
import com.sandbox.sheka.dto.payload.PayloadDto;
import com.sandbox.sheka.services.PaymentService;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static com.sandbox.sheka.controllers.PaymentRestController.extractToken;

@Controller
public class PaymentController
{
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final PaymentService paymentService;

    public PaymentController(OAuth2AuthorizedClientService authorizedClientService, PaymentService paymentService)
    {
        this.authorizedClientService = authorizedClientService;
        this.paymentService = paymentService;
    }

    @PostMapping("/payment-requests")
    @ResponseBody
    public ResponseEntity<String> paymentRequests(@ModelAttribute PaymentPageDto paymentPageDto, BindingResult result, OAuth2AuthenticationToken authentication, HttpSession httpSession)
    {
        OAuth2AccessToken token = extractToken(authorizedClientService, authentication);

        PayloadDto payloadDto = PayloadDto.builder()
            .beneficiary(new BeneficiaryDto(new CreditorDto(paymentPageDto.getName()), new CreditorAccountDto(paymentPageDto.getIban())))
            .creditTransferTransaction(new CreditTransferTransactionDto(
                new InstructedAmountDto(paymentPageDto.getAmount(), paymentPageDto.getCurrency()), null, null))
            .build();

        return ResponseEntity.ok(paymentService.pay(token, payloadDto));
    }

    @GetMapping("/payment")
    public String greetingForm(Model model) {
        model.addAttribute("paymentPageDto", new PaymentPageDto());
        return "page";
    }

}
