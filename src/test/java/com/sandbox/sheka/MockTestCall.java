
package com.sandbox.sheka;

import java.io.IOException;
import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockTestCall implements Call<String>
{
    @Override
    public Response<String> execute()
    {
        return Response.success("ok");
    }

    @Override
    public void enqueue(Callback<String> callback)
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
    public Call<String> clone()
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
