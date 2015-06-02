package com.rainbow.iSecKill.net;

import com.squareup.okhttp.*;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Chen on 2015/6/2.
 */
public class RequestSender {

    private static final OkHttpClient client = new OkHttpClient();

    static {
        client.setConnectTimeout(15, TimeUnit.SECONDS);

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
    }

    /**
     * 该不会开启异步线程。
     * @param request
     * @return
     */
    public static Response execute(Request request) {
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 开启异步线程访问网络
     * @param url
     * @param responseCallback
     */
    public static void enqueue(String url, Callback responseCallback) {
        client.newCall(getRequest(url, null)).enqueue(responseCallback);
    }

    public static String execute(String url, Map<String, String> postParams) {
        Response response = execute(getRequest(url, postParams));
        String responseStr = null;
        if (response != null && response.isSuccessful()) {
            try {
                if (response.isRedirect()) {

                } else {
                    responseStr = response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseStr;
    }

    public static String execute(String url) {
        return execute(url, null);
    }

    public static Request getRequest(String url, Map<String, String> params) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko");
        builder.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        builder.header("Accept-Encoding", "gzip, deflate, sdch");
        builder.header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");

        if (params != null && params.size() > 0) {
            FormEncodingBuilder formBody = new FormEncodingBuilder();
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                formBody.add(entry.getKey(), entry.getValue());
            }
            builder.post(formBody.build());
        }

        return builder.build();
    }
}
