package soexample.umeng.com.myproject.net;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import soexample.umeng.com.myproject.listener.HttpListener;

public class HelperUtils {

    private BaseService baseService;

    public HelperUtils() {
        Retrofit retrofit = new Retrofit.Builder()
                //适配器工厂
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.zhaoapi.cn/")
                .build();
        baseService = retrofit.create(BaseService.class);

    }
    //get

    public HelperUtils get(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseService.get(url, map)
                //设置调度器
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//主线程
                .subscribe(observer);//添加观察者
        return this;
    }

    //post
    public HelperUtils post(String url, Map<String, String> map) {
        if (map == null) {

            map = new HashMap<>();
        }
        baseService.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return this;
    }

    //公用一个观察者
    private Observer observer = new Observer<ResponseBody>() {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String string = responseBody.string();
                listener.success(string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            String message = e.getMessage();
            listener.fail(message);
        }

        @Override
        public void onComplete() {

        }
    };

    //接口回调
    private HttpListener listener;

    public void result(HttpListener listener) {
        this.listener = listener;
    }
}
