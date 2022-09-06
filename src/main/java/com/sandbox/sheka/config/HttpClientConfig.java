package com.sandbox.sheka.config;

import com.sandbox.sheka.httpclient.RetrofitHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.sandbox.sheka.config.utils.Constants.RESOURCE_OWNER_URL;

@Configuration
public class HttpClientConfig
{

    @Bean
    public RetrofitHttpClient retrofitHttpClient(){
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RESOURCE_OWNER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        return retrofit.create(RetrofitHttpClient.class);
    }

}
