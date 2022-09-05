package com.sandbox.sheka.facade;


import java.io.IOException;
import com.sandbox.sheka.dto.payload.PayloadDto;
import com.sandbox.sheka.httpclient.RetrofitHttpClient;
import okhttp3.ResponseBody;
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
    public String pay(OAuth2AuthorizedClient client, PayloadDto payload)
    {

        Response<ResponseBody> execute;
        ResponseBody body;
        String result = null;

        StringBuilder token = new StringBuilder();
        token.append(client.getAccessToken().getTokenType().getValue())
            .append(' ')
            .append(client.getAccessToken().getTokenValue());

        try
        {
            execute = retrofitHttpClient.paymentRequests(token.toString(), payload).execute();
            body = execute.body();
            //todo Method invocation 'string' may produce 'NullPointerException'
            result = body.string();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
