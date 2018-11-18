package com.bwie.mnzk5.utils;

import android.os.Handler;

import com.bwie.mnzk5.inter.ICallBack;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * date:2018/11/16
 * author:mxy(M)
 * function:
 */
public class HttpUtils {
    private static  volatile HttpUtils instance;

    private OkHttpClient client;

    private Handler handler=new Handler();

    public HttpUtils(){
        client=new OkHttpClient();
    }


    public static  HttpUtils getInstance(){
        if (instance==null){
            synchronized (HttpUtils.class){
                if (instance==null){
                    instance=new HttpUtils();
                }
            }
        }
        return instance;
    }

    public void get(String url, final ICallBack callBack, final Type type){
        Request request=new Request.Builder()
                .get()
                .url(url)
                .build();

        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    Gson gson=new Gson();
                    final Object o = gson.fromJson(result, type);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(o);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
