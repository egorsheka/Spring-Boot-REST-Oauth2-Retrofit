package com.sandbox.sheka.config;

import java.util.List;
import com.sandbox.sheka.auth.CustomAuthorizationCodeTokenResponseClient;
import com.sandbox.sheka.converters.spring.CustomOAuth2AuthorizationCodeGrantRequestEntityConverter;
import com.sandbox.sheka.httpclient.RetrofitHttpClient;
import com.sandbox.sheka.auth.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Value("${spring.security.oauth2.client.registration.sandbox.scope}")
    private List<String> scopes;

    final RetrofitHttpClient retrofitHttpClient;

    public OAuth2SecurityConfig(RetrofitHttpClient retrofitHttpClient)
    {
        this.retrofitHttpClient = retrofitHttpClient;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
            authorizeRequests()
            .antMatchers("/payment").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()
            .tokenEndpoint()
            .accessTokenResponseClient(accessTokenResponseClient());

        http.oauth2Login()
            .userInfoEndpoint()
            .userService(oauth2UserService());
    }

    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient()
    {
        CustomOAuth2AuthorizationCodeGrantRequestEntityConverter converter = new CustomOAuth2AuthorizationCodeGrantRequestEntityConverter(scopes);

        return new CustomAuthorizationCodeTokenResponseClient(converter, retrofitHttpClient);
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new CustomOAuth2UserService();
    }



}
