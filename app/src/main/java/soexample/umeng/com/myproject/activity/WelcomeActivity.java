package soexample.umeng.com.myproject.activity;

import soexample.umeng.com.myproject.mvp.presenter.BaseActivityPresenter;
import soexample.umeng.com.myproject.presenter.WelcomeActivityPresenter;

public class WelcomeActivity extends BaseActivityPresenter<WelcomeActivityPresenter> {
    @Override
    public Class<WelcomeActivityPresenter> getClassDeleGate() {
        return WelcomeActivityPresenter.class;
    }
}
