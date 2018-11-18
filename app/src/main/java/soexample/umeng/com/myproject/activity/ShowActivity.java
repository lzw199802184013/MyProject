package soexample.umeng.com.myproject.activity;

import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.mvp.presenter.BaseActivityPresenter;
import soexample.umeng.com.myproject.presenter.ShowActivityPresenter;

public class ShowActivity extends BaseActivityPresenter<ShowActivityPresenter> {

    @Override
    public Class<ShowActivityPresenter> getClassDeleGate() {
        return ShowActivityPresenter.class;
    }


}