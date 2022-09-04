package com.sandbox.sheka.converters.spring;

import java.util.List;
import com.sandbox.sheka.config.utils.Constants;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.util.MultiValueMap;

public class CustomOAuth2AuthorizationCodeGrantRequestEntityConverter extends OAuth2AuthorizationCodeGrantRequestEntityConverter
{

    private final List<String> scopes;

    public CustomOAuth2AuthorizationCodeGrantRequestEntityConverter(List<String> scopes)
    {
        this.scopes = scopes;
    }

    @Override
    protected MultiValueMap<String, String> createParameters(
        OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest)
    {
        MultiValueMap<String, String> parameters = super.createParameters(authorizationCodeGrantRequest);
        parameters.put(Constants.SCOPE, scopes);
        return parameters;
    }

}
