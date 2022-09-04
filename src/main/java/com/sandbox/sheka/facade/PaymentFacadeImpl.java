package com.sandbox.sheka.facade;


import java.io.IOException;
import com.sandbox.sheka.dto.payload.BeneficiaryDto;
import com.sandbox.sheka.dto.payload.CreditTransferTransactionDto;
import com.sandbox.sheka.dto.payload.CreditorAccountDto;
import com.sandbox.sheka.dto.payload.CreditorDto;
import com.sandbox.sheka.dto.payload.InstructedAmountDto;
import com.sandbox.sheka.dto.payload.PayloadDto;
import com.sandbox.sheka.dto.payload.PaymentTypeInformationDto;
import com.sandbox.sheka.dto.payload.RemittanceInformationDto;
import com.sandbox.sheka.httpclient.RetrofitHttpClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class PaymentFacadeImpl implements PaymentFacade
{
    private final RetrofitHttpClient retrofitHttpClient;

    public PaymentFacadeImpl(RetrofitHttpClient retrofitHttpClient)
    {
        this.retrofitHttpClient = retrofitHttpClient;
    }

    @Override
    public String pay(OAuth2AuthorizedClient client)
    {

        PayloadDto payload = PayloadDto.builder()
            .beneficiary(new BeneficiaryDto(new CreditorDto("Ben"), new CreditorAccountDto("DHDH34HF73r3HFIRFDH43rf")))
            .creationDateTime("date")
            .creditTransferTransaction(new CreditTransferTransactionDto(new InstructedAmountDto(1, "byn"), new RemittanceInformationDto(""), "date"))
            .numberOfTransactions(1)
            .paymentInformationId("123435")
            .paymentTypeInformation(new PaymentTypeInformationDto("1", "1", "1"))
            .build();

        Response<String> execute;
        String body = null;
        try
        {
            execute = retrofitHttpClient.paymentRequests(client.getAccessToken().getTokenType() + " " + client.getAccessToken().getTokenValue(), payload).execute();
            body = execute.body();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return body;
    }
}
