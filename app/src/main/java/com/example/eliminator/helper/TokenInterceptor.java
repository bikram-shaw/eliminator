package com.example.eliminator.helper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    private String token;
public TokenInterceptor(String token)
{
    this.token=token;
}
    @Override
    public Response intercept(Chain chain) throws IOException {

        //rewrite the request to add bearer token
        Request newRequest=chain.request().newBuilder()
                .header("Authorization","JWT "+ token)
                .build();

        return chain.proceed(newRequest);
    }
}