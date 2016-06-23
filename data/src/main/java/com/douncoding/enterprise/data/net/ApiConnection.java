package com.douncoding.enterprise.data.net;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Api Connection class used to retrieve data from the cloud.
 *
 * 매요청마다 새로운 새션을 생성(?)
 * 현재는 "GET" 메소드만 지원하는 형태
 *
 * 이거 업데이트 시급하네... 진짜 샘플소스가 맞았네...
 * Retrofit2 를 사용하는 형태로 변경할 것!
 */
public class ApiConnection implements Callable<String> {

    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";

    private URL url;
    private String response;

    private ApiConnection(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public static ApiConnection createGET(String url) throws MalformedURLException {
        return new ApiConnection(url);
    }

    private OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10000, TimeUnit.MILLISECONDS);
        builder.connectTimeout(15000, TimeUnit.MILLISECONDS);
        return builder.build();
    }

    private void connectToApi() {
        OkHttpClient okHttpClient = this.createClient();

        Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .get()
                .build();

        try {
            this.response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String call() throws Exception {
        return requestSyncCall();
    }

    @Nullable
    public String requestSyncCall() {
        connectToApi();
        return response;
    }
}
