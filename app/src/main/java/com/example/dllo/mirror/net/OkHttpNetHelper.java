package com.example.dllo.mirror.net;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by dllo on 16/3/30.
 */
public class OkHttpNetHelper {

    // 对于加载网络数据,比如获取网页的内容
    // 请求网络数据的方法

    // post请求,参数是包含在请求体中的,通常这里通过FormEncodingBuilder,添加多个键值对,既然添加多个键值对，
    // 就要在用的时候再调用builder往里面add,然后去构造RequestBody,最后完成Request的构造
    // URL, 指拼接完成后的网址, 已经在常量类里面提出，可以直接用静态常量
    public void getPostDataFromNet(FormEncodingBuilder builder, String URL, final NetListener netListener) {

        // 创建okHttpClient 对象
        OkHttpClient okHttpClient = new OkHttpClient();

        // 创建一个Request
        Request request = new Request.Builder().url(URL).post(builder.build()).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                netListener.getFail("拉取失败");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String htmlData = response.body().string();
                // 回调, 返回给Activity
                netListener.getSuccess(htmlData);

            }
        });
    }

    // 所以在调用的时候就是
//    OkHttpNetHelper okHttpNetHelper = new OkHttpNetHelper();
//    FormEncodingBuilder builder = new FormEncodingBuilder();
//    builder.add("K","V");

    // 在请求出来数据的时候, 复写runOnUIThread 方法, 在主线程中刷新UI


}
