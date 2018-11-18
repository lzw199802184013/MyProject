package soexample.umeng.com.myproject.activity;

import soexample.umeng.com.myproject.mvp.presenter.BaseActivityPresenter;
import soexample.umeng.com.myproject.presenter.LoginActivityPresenter;

public class LoginActivity extends BaseActivityPresenter<LoginActivityPresenter> {

    @Override
    public Class<LoginActivityPresenter> getClassDeleGate() {
        return LoginActivityPresenter.class;
    }
}
