package com.sandbox.sheka;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import com.google.gson.Gson;
import com.sandbox.sheka.dto.payload.PayloadDto;
import com.sandbox.sheka.httpclient.RetrofitHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class Oauth2ApplicationTests
{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    ClientRegistrationRepository registrations;

    @MockBean
    private RetrofitHttpClient retrofitHttpClient;



    @Test
    public void should_return_405() throws Exception
    {

        OAuth2AuthenticationToken authenticationToken = createToken();
        OAuth2AuthorizedClient authorizedClient = createAuthorizedClient(authenticationToken);

        Mockito.when(this.authorizedClientService.loadAuthorizedClient(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(authorizedClient);

        this.mockMvc.perform(post("/api/v1/payment-requests").with(authentication(authenticationToken)).contentType(APPLICATION_JSON)
            .content(buildPayloadJson()).with(csrf()))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void should_return_200() throws Exception
    {

        OAuth2AuthenticationToken authenticationToken = createToken();
        OAuth2AuthorizedClient authorizedClient = createAuthorizedClient(authenticationToken);


        Mockito.when(this.authorizedClientService.loadAuthorizedClient(Mockito.anyString(), Mockito.anyString()))
            .thenReturn(authorizedClient);
        Mockito.when(retrofitHttpClient.paymentRequests(Mockito.anyString(), Mockito.any())).thenReturn(new MockTestCall());


        this.mockMvc.perform(post("/api/v1/payment-requests").with(authentication(authenticationToken)).contentType(APPLICATION_JSON)
                .content(buildPayloadJson()).with(csrf()))
            .andExpect(status().isOk());
    }

    private String buildPayloadJson(){
        Gson gson = new Gson();
        PayloadDto payloadDto = PayloadDto.builder().build();
        return gson.toJson(payloadDto);
    }

    private OAuth2AuthorizedClient createAuthorizedClient(OAuth2AuthenticationToken authenticationToken) {
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "token", Instant.now(), Instant.now().plus(
            Duration.ofDays(1)));

        ClientRegistration clientRegistration = this.registrations.findByRegistrationId(authenticationToken.getAuthorizedClientRegistrationId());
        return new OAuth2AuthorizedClient(clientRegistration, authenticationToken.getName(), accessToken);
    }

    private OAuth2AuthenticationToken createToken() {
        Set<GrantedAuthority> authorities = new HashSet<>(AuthorityUtils.createAuthorityList("USER"));
        OAuth2User oAuth2User = new DefaultOAuth2User(authorities, Collections.singletonMap("name", "user"), "name");
        return new OAuth2AuthenticationToken(oAuth2User, authorities, "sandbox");
    }

}