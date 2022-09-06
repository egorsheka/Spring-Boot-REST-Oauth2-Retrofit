package com.sandbox.sheka.auth;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sandbox.sheka.dto.Token;
import com.sandbox.sheka.exceptions.RequestEntityConvertException;
import com.sandbox.sheka.httpclient.RetrofitHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.web.server.ResponseStatusException;
import retrofit2.Call;
import static com.sandbox.sheka.config.utils.Constants.CLIENT_ID;
import static com.sandbox.sheka.config.utils.Constants.CLIENT_SECRET;
import static com.sandbox.sheka.config.utils.Constants.CODE;
import static com.sandbox.sheka.config.utils.Constants.SCOPE;


public class CustomAuthorizationCodeTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>
{
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthorizationCodeTokenResponseClient.class);

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
        if (request == null)
        {
            throw new RequestEntityConvertException("Cannot convert authorizationGrantRequest " + authorizationGrantRequest);
        }
        Token response = getResponse(request.getBody());

        return OAuth2AccessTokenResponse
            .withToken(response.getAccessToken())
            .tokenType(OAuth2AccessToken.TokenType.BEARER.getValue().equals(response.getTokenType()) ? OAuth2AccessToken.TokenType.BEARER : null)
            .expiresIn(Long.parseLong(response.getExpiresIn()))
            .scopes(Set.of(response.getScope()))
            .additionalParameters(getPayLoadFromToken(response.getAccessToken()))
            .build();
    }

    private Map<String, Object> getPayLoadFromToken(String token)
    {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        Gson gson = new Gson();
        return gson.fromJson(payload, new TypeToken<Map<String, Object>>() {}.getType());
    }

    private Token getResponse(Object parameters)
    {

        Map<String, List<String>> queriesListMap = (Map<String, List<String>>) parameters;

        if (queriesListMap == null)
        {
            throw new RequestEntityConvertException("Parameters of request couldn't be null");
        }

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

        try
        {
            return token.execute().body();
        }
        catch (IOException e)
        {
            logger.error("the authorization server is not responding", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the authorization server is not responding");
        }

    }
}