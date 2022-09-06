package com.sandbox.sheka;

import com.sandbox.sheka.httpclient.RetrofitHttpClient;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class Oauth2IntegrationMockTest extends BaseOauth2Test
{

    @MockBean
    private RetrofitHttpClient retrofitHttpClient;


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


}