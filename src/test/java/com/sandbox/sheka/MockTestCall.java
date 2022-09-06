package com.sandbox.sheka;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockTestCall implements Call<ResponseBody>
{
    @Override
    public Response<ResponseBody> execute()
    {
        return Response.success(ResponseBody.create(MediaType.get("application/json"), "ok"));
    }

    @Override
    public void enqueue(Callback<ResponseBody> callback)
    {
        throw new IllegalCallerException();
    }

    @Override
    public boolean isExecuted()
    {
        return false;
    }

    @Override
    public void cancel()
    {
        throw new IllegalCallerException();

    }

    @Override
    public boolean isCanceled()
    {
        return false;
    }

    @Override
    public Call<ResponseBody> clone()
    {
        return null;
    }

    @Override
    public Request request()
    {
        return null;
    }

    @Override
    public Timeout timeout()
    {
        return null;
    }
}
