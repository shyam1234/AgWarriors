package com.aiml.agwarriors.restfulManager;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class WebApplication {
    private static WebApplication instance;
    private OkHttpClient client;

    private WebApplication() {
        client = new OkHttpClient();
    }

    public static WebApplication getInstance() {
        if (instance == null) {
            instance = new WebApplication();
        }
        return instance;
    }


    public void getResponse(String pURL, final IWebApp pCallback) {
        try {
            client.newCall(new Request.Builder()
                    .url(pURL)
                    .build()).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    pCallback.onFailure(request, e);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                   pCallback.onResponse(response.body().string());
                }
            });
        } catch (Exception e) {
        }
    }

    public void getPostResponse(String pURL, String pRegJson, final IWebApp pCallback) {
        try {
            MediaType contentType = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(contentType, pRegJson);
            client.newCall(new Request.Builder()
                    .url(pURL)
                    .post(body)
                    .build()).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                      pCallback.onFailure(request,e);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    pCallback.onResponse(response.body().string());
                }
            });
        } catch (Exception e) {
        }
    }

    public static interface IWebApp {
        void onResponse(String response);
        void onFailure(Request request, IOException e);
    }
}
