package com.sandbox.sheka.config;

import java.util.List;
import com.sandbox.sheka.converters.spring.CustomOAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;

@EnableWebSecurity
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Value("${spring.security.oauth2.client.registration.sandbox.scope}")
    private List<String> scopes;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
            authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()
            .tokenEndpoint()
            .accessTokenResponseClient(accessTokenResponseClient());
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient()
    {
        DefaultAuthorizationCodeTokenResponseClient authorizationCodeTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
        authorizationCodeTokenResponseClient.setRequestEntityConverter(new CustomOAuth2AuthorizationCodeGrantRequestEntityConverter(scopes));
        return authorizationCodeTokenResponseClient;
    }



}
