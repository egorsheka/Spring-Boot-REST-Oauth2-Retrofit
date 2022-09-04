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

package com.sandbox.sheka.httpclient;

import com.sandbox.sheka.dto.Payments;
import com.sandbox.sheka.dto.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitHttpClient
{

    @POST("/token")
    Call<Token> getToken(
        @Query("client_id") Object client_id,
        @Query("client_secret") Object client_secret,
        @Query("code") Object code,
        @Query("scope") Object scope);

    @GET("/signin")
    Call<Token> singIn(
        @Query("client_id") String client_id,
        @Query("redirect_uri") String redirect_uri,
        @Query("response_type") String response_type,
        @Query("scope") String scope,
        @Query("state") String state);


    @POST("/payment-requests")
    Call<Token> paymentRequests(@Header("Authorization") String token, @Body Payments payments);


}
