package com.sandbox.sheka.services.impl;


import java.io.IOException;
import com.sandbox.sheka.dto.payload.PayloadDto;
import com.sandbox.sheka.httpclient.RetrofitHttpClient;
import com.sandbox.sheka.services.PaymentService;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Response;

@Service
public class PaymentServiceImpl implements PaymentService
{
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final RetrofitHttpClient retrofitHttpClient;

    public PaymentServiceImpl(RetrofitHttpClient retrofitHttpClient)
    {
        this.retrofitHttpClient = retrofitHttpClient;
    }

    @Override
    public String pay(OAuth2AccessToken token, PayloadDto payload)
    {
        Response<ResponseBody> execute;
        ResponseBody response;

        String fullToken = token.getTokenType().getValue()
            .concat(" ")
            .concat(token.getTokenValue());

        try
        {
            execute = retrofitHttpClient.paymentRequests(fullToken, payload).execute();
            response = execute.body();
            if (response == null)
            {
                throw new IOException();
            }
            return response.string();
        }
        catch (IOException e)
        {
            logger.error("payment resource server is not responding", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment resource server is not responding");
        }
    }
}
