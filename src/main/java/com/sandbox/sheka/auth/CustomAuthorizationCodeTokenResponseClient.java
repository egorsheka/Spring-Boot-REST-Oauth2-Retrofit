package com.sandbox.sheka.auth;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import com.sandbox.sheka.dto.Token;
import com.sandbox.sheka.httpclient.RetrofitHttpClient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.util.Assert;
import retrofit2.Call;
import retrofit2.Response;
import static com.sandbox.sheka.config.utils.Constants.CLIENT_ID;
import static com.sandbox.sheka.config.utils.Constants.CLIENT_SECRET;
import static com.sandbox.sheka.config.utils.Constants.CODE;
import static com.sandbox.sheka.config.utils.Constants.SCOPE;


public class CustomAuthorizationCodeTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>
{

    private final Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> requestEntityConverter;

    private final RetrofitHttpClient retrofitHttpClient;

    public CustomAuthorizationCodeTokenResponseClient(
        Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> requestEntityConverter, RetrofitHttpClient retrofitHttpClient)
    {
        this.requestEntityConverter = requestEntityConverter;
        this.retrofitHttpClient = retrofitHttpClient;
    }


    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest)
    {
        RequestEntity<?> request = this.requestEntityConverter.convert(authorizationGrantRequest);
        Token response = getResponse(request);

        OAuth2AccessTokenResponse tokenResponse = OAuth2AccessTokenResponse
            .withToken(response.getAccessToken())
            .tokenType(OAuth2AccessToken.TokenType.BEARER.getValue().equals(response.getTokenType()) ? OAuth2AccessToken.TokenType.BEARER : null)
            .expiresIn(Long.parseLong(response.getExpiresIn()))
            .scopes(Set.of(response.getScope()))
            .build();

        return tokenResponse;
    }

    private Token getResponse(RequestEntity<?> request)
    {

        Map<String, List<String>> queriesListMap = (Map<String, List<String>>) request.getBody();
        Assert.notNull(queriesListMap, "queriesListMap cannot be null");

        Map<String, String> queries = queriesListMap
            .entrySet()
            .stream()
            .flatMap(e -> e.getValue().stream().map(v -> new AbstractMap.SimpleEntry<>(e.getKey(), v)))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Call<Token> token = retrofitHttpClient.getToken(
            queries.get(CLIENT_ID),
            queries.get(CLIENT_SECRET),
            queries.get(CODE),
            queries.get(SCOPE));

        Response<Token> tokenResponse = null;
        try
        {
            tokenResponse = token.execute();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return Objects.requireNonNull(tokenResponse).body();
    }
}