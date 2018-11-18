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
import soexample.umeng.com.myproject.model.RegisterBean;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.net.Http;
import soexample.umeng.com.myproject.net.HttpUtils;

public class RegisterActivityPresenter extends ADeleGate implements View.OnClickListener {
    private Context context;
    private EditText register_name, register_pass, register_pass2;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initData() {
        super.initData();
        setClick(this, R.id.register_login);
        register_name = (EditText) get(R.id.register_name);
        register_pass = (EditText) get(R.id.register_pass);
        register_pass2 = (EditText) get(R.id.register_pass2);
    }


    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_login:
                doRegister();
                break;
        }
    }

    private void doRegister() {


        String registerName = register_name.getText().toString().trim();
        if (TextUtils.isEmpty(registerName)) {
            toast(context, "账号不能为空");
            return;
        }
        String registerPass = register_pass.getText().toString().trim();
        if (TextUtils.isEmpty(registerPass)) {
            toast(context, "密码不能为空");
            return;
        }
        String registerPass2 = register_pass2.getText().toString().trim();
        if (!registerPass2.equals(registerPass)) {
            toast(context, "请保持密码一致");
            return;
        }
        new HttpUtils().get(Http.ZHUCE_URL + "?mobile=" + registerName + "&password=" + registerPass).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(data, RegisterBean.class);
                if ("0".equals(registerBean.getCode())) {
                    toast(context, data);
                    context.startActivity(new Intent(context, LoginActivity.class));
                    ((RegisterActivity) context).finish();


                }

            }

            @Override
            public void fail(String error) {

            }
        });
    }
}
