package soexample.umeng.com.myproject.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import soexample.umeng.com.myproject.listener.HttpListener;

public class HttpUtils {
    private static final int HTTP_FAIL = 101;
    private static final int HTTP_SUCCESS = 100;
    private HttpListener listener;

    public HttpUtils() {

    }

    ;
    private static HttpUtils httpUtils;

    public static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    //get请求
    public HttpUtils get(String url) {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String method = request.method();
                String host = request.url().host();
                Log.i("intercept", method + "====" + host);
                return chain.proceed(request);
            }
        }).build();
        Request request = new Request.Builder().url(url).build();
        try {
            doHttp(client, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    //post请求
    public HttpUtils post(String url, RequestBody body) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String method = request.method();
                String host = request.url().host();
                Log.i("intercept", method + "====" + host);
                return chain.proceed(request);
            }
        }).build();
        Request request = new Request.Builder().url(url).post(body).build();
        try {
            doHttp(client, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void doHttp(OkHttpClient client, Request request) throws IOException {
        final Message message = Message.obtain();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                message.what = HTTP_FAIL;
                message.obj = e.getMessage();
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                message.what = HTTP_SUCCESS;
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HTTP_SUCCESS:
                    String data = (String) msg.obj;
                    listener.success(data);
                    break;
                case HTTP_FAIL:
                    String error = (String) msg.obj;
                    listener.fail(error);
                    break;
            }
        }
    };
    //接口回调
    public void result(HttpListener listener) {
        this.listener = listener;
    }


}
