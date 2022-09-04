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
