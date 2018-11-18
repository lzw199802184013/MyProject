package soexample.umeng.com.myproject.activity;

import soexample.umeng.com.myproject.mvp.presenter.BaseActivityPresenter;
import soexample.umeng.com.myproject.presenter.RegisterActivityPresenter;

public class RegisterActivity extends BaseActivityPresenter<RegisterActivityPresenter> {
    @Override
    public Class<RegisterActivityPresenter> getClassDeleGate() {
        return RegisterActivityPresenter.class;
    }
}
