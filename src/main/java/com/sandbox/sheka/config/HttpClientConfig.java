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

package com.sandbox.sheka.config;

import com.sandbox.sheka.httpclient.RetrofitHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class HttpClientConfig
{

    @Bean
    public RetrofitHttpClient retrofitHttpClient(){
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://test.devenv.smartym.by")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(RetrofitHttpClient.class);
    }

}
