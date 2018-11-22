package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.LoginActivity;
import soexample.umeng.com.myproject.activity.RegisterActivity;
import soexample.umeng.com.myproject.listener.HttpListener;
import soexample.umeng.com.myproject.model.LoginBean;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.net.Http;
import soexample.umeng.com.myproject.net.HttpUtils;
import soexample.umeng.com.myproject.utils.SharedPreferencesUtils;

public class LoginActivityPresenter extends ADeleGate implements View.OnClickListener {
    private Context context;
    private EditText user_name, user_pass;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        super.initData();
        setClick(this, R.id.user_register);
        setClick(this, R.id.user_login);
        user_name = (EditText) get(R.id.user_name);
        user_pass = (EditText) get(R.id.user_pass);
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_register://注册
                context.startActivity(new Intent(context, RegisterActivity.class));
                break;
            case R.id.user_login://登录
                doLogin();
                break;
        }
    }

    //登录
    private void doLogin() {
        final String userName = user_name.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            toast(context, "请输入手机号码");
            return;
        }
        if (userName.length() != 11) {
            toast(context, "请输入正确的手机号");
            return;
        }
        String userPass = user_pass.getText().toString().trim();
        if (TextUtils.isEmpty(userPass)) {
            toast(context, "请输入密码");
            return;

        }
        new HttpUtils().get(Http.LOGIN_URL + "?mobile=" + userName + "&password=" + userPass).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(data, LoginBean.class);
                if ("0".equals(loginBean.getCode())) {
                    if (loginBean.getData().getNickname() != null) {
                        String nickName = loginBean.getData().getNickname() + "";
                        SharedPreferencesUtils.putString(context, "nickname", nickName);
                    }
                    if (loginBean.getData().getIcon() != null) {
                        String icon = (String) loginBean.getData().getIcon();
                        SharedPreferencesUtils.putString(context, "icon", icon);
                    }
                    if (loginBean.getData().getMoney() != null) {
                        String money = loginBean.getData().getMoney() + "";
                        SharedPreferencesUtils.putString(context, "money", money);
                    }
                    SharedPreferencesUtils.putString(context, "userName", loginBean.getData().getUsername());
                    SharedPreferencesUtils.putString(context, "token", loginBean.getData().getToken());
                    SharedPreferencesUtils.putString(context, "uid", loginBean.getData().getUid());
                    SharedPreferencesUtils.putString(context,"cars_refresh","3");//登陆成功
                    ((LoginActivity) context).finish();

                } else {
                    toast(context, "用户名密码不正确，请重新输入");
                    return;

                }
            }

            @Override
            public void fail(String error) {

            }
        });


    }
}
