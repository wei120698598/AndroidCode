package com.wei.sample.okhttp;

import com.wei.sample.utils.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description
 * @date 2019-05-23
 * @email weishuxin@icourt.cc
 */
public class TestOkHttp {
    String url = "http://wthrcdn.etouch.cn/weather_mini?city=沈阳";

    public void async() {
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = getHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.sout("onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.sout("onResponse: " + response.body().string());
            }
        });
    }

    public void sync() {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = getHttpClient().newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    Logger.sout("run: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder(new OkHttpClient())
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }


    public static void main(String[] args) {
        new TestOkHttp().async();
        new TestOkHttp().sync();
    }
}
