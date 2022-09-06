/*
 **********************************************************************
 * Copyright SaM Solutions, 2021. All rights reserved.
 * All rights to the source code herein are property of the SaM Solutions.
 * Use and / or receipt of user rights to the source code and / or making amendments and adjustments, which can influence
 * functional properties and characteristics of the code, are possible only with the written permission of the SaM Solutions
 * company, or according to the terms and conditions stipulated in the agreement / contract, on the basis of which the source
 * code has been developed.
 * This copyright notice must not be removed.
 **********************************************************************
 */

package com.sandbox.sheka;


import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import com.google.gson.Gson;
import com.sandbox.sheka.dto.payload.PayloadDto;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BaseOauth2Test
{

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    ClientRegistrationRepository registrations;

    public String buildPayloadJson(){
        Gson gson = new Gson();
        PayloadDto payloadDto = PayloadDto.builder().build();
        return gson.toJson(payloadDto);
    }

    public OAuth2AuthorizedClient createAuthorizedClient(OAuth2AuthenticationToken authenticationToken) {
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "token", Instant.now(), Instant.now().plus(
            Duration.ofDays(1)));

        ClientRegistration clientRegistration = registrations.findByRegistrationId(authenticationToken.getAuthorizedClientRegistrationId());
        return new OAuth2AuthorizedClient(clientRegistration, authenticationToken.getName(), accessToken);
    }

    public OAuth2AuthenticationToken createToken() {
        Set<GrantedAuthority> authorities = new HashSet<>(AuthorityUtils.createAuthorityList("USER"));
        OAuth2User oAuth2User = new DefaultOAuth2User(authorities, Collections.singletonMap("name", "user"), "name");
        return new OAuth2AuthenticationToken(oAuth2User, authorities, "sandbox");
    }
}
