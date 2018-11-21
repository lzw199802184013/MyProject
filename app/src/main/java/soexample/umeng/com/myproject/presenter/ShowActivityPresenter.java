package soexample.umeng.com.myproject.presenter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.ShowActivity;
import soexample.umeng.com.myproject.listener.HttpListener;
import soexample.umeng.com.myproject.model.DetaUtilsBean;
import soexample.umeng.com.myproject.model.FenYeBean;
import soexample.umeng.com.myproject.model.LeiBieBean;
import soexample.umeng.com.myproject.model.PubuBean;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.net.Http;
import soexample.umeng.com.myproject.net.HttpUtils;
import soexample.umeng.com.myproject.utils.SharedPreferencesUtils;

public class ShowActivityPresenter extends ADeleGate implements View.OnClickListener {
    private Context context;
    private WebView webview;
    private WebSettings settings;
    private RelativeLayout bar;
    private ImageView shopImage;
    private TextView shopPrice, shopTitle;
    private RelativeLayout layoutCar;
    private DetaUtilsBean bean;
    private int sellerid;//商家id
    private String pid;
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_show;
    }

    @Override
    public void initData() {
        super.initData();
        bar = (RelativeLayout) get(R.id.bar);
        webview = (WebView) get(R.id.webview);
        layoutCar = (RelativeLayout) get(R.id.layout_shop_add_car);
        shopImage = (ImageView) get(R.id.shop_image);
        shopPrice = (TextView) get(R.id.shop_price);
        shopTitle = (TextView) get(R.id.shop_title);
        setClick(this, R.id.jiaru, R.id.shop_close, R.id.sure, R.id.ll);
        Intent intent = ((ShowActivity) context).getIntent();
        url = intent.getStringExtra("url");
        pid = intent.getStringExtra("pid");
        //获取序列化对象
        FenYeBean.DataBean list = (FenYeBean.DataBean) intent.getSerializableExtra("list");
        if (list != null) {
            pid = list.getPid() + "";
            url = list.getDetailUrl();
        }
        LeiBieBean.DataBean list2 = (LeiBieBean.DataBean) intent.getSerializableExtra("list2");
        if (list2 != null) {
            pid = list2.getPid() + "";
            url = list2.getDetailUrl();
        }
        toast(context, pid + "");
        try {
            setData(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        doHttp(pid);

    }

    private void doHttp(String pid) {
        new HttpUtils().get(Http.SHOP_DETAILS_URL + pid).result(new HttpListener() {
            @Override
            public void success(String data) {
//                toast(context, data);
                bean = new Gson().fromJson(data, DetaUtilsBean.class);
                sellerid = bean.getSeller().getSellerid();
            }

            @Override
            public void fail(String error) {

            }
        });
    }


    private void setData(String url) throws Exception {
        settings = webview.getSettings();
        settings.setUseWideViewPort(true);//设置加载进来的页面自适应屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setUseWideViewPort(false);//禁止webview做自动缩放
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setSupportMultipleWindows(false);
        settings.setAppCachePath(context.getDir("cache", Context.MODE_PRIVATE).getPath());
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.setFocusable(true);
        webview.requestFocus();
        webview.setWebChromeClient(new WebChromeClient());  //解决android与H5协议交互,弹不出对话框问题
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //页面加载完成之后
                bar.setVisibility(View.GONE);
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webview.loadUrl(url);
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    context.startActivity(intent);
                    return true;
                }
                return true;

            }
        });
        webview.loadUrl(url);
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jiaru://加入
                showShopCar();
                break;
            case R.id.shop_close:
                //关闭
                hintShopCar();
                break;
            case R.id.sure:
                //确定
                doGet(pid);
                break;
            //购物车
            case R.id.ll:
                SharedPreferencesUtils.putString(context, "cars_refresh", "0");
                ((ShowActivity) context).finish();
                break;

        }
    }


    //添加购物车
    private void doGet(String pid) {
        String uid = SharedPreferencesUtils.getString(context, "uid");
        String token = SharedPreferencesUtils.getString(context, "token");
        if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(token)) {
            toast(context, "你还没有登录，请先去登录");
            return;
        }
        new HttpUtils().get(Http.AddCar_URL + "?uid=" + uid + "&token=" + token + "&pid=" + pid).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("null")) {
                    toast(context, "购物车暂时没有商品啊");
                    return;
                } else {
                    toast(context, "加购成功");
                    SharedPreferencesUtils.putString(context, "success", "4");
                    hintShopCar();
                }
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //显示
    private void showShopCar() {
        int height = context.getResources().getDisplayMetrics().heightPixels;

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layoutCar, "translationY", height, 600);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        layoutCar.setVisibility(View.VISIBLE);
        if (bean != null) {
            shopTitle.setText(bean.getData().getTitle());
            shopPrice.setText(bean.getData().getPrice() + "");
            Glide.with(context).load(bean.getData().getImages().split("\\|")[0]).into(shopImage);

        }
    }
    //关闭

    private void hintShopCar() {
        int height = context.getResources().getDisplayMetrics().heightPixels;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(layoutCar, "translationY", 600, height);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutCar.setVisibility(View.GONE);
            }
        }, 1000);

    }
}
