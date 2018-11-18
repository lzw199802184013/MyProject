package soexample.umeng.com.myproject.activity;

import soexample.umeng.com.myproject.mvp.presenter.BaseActivityPresenter;
import soexample.umeng.com.myproject.presenter.LiuShiActivityPresenter;

public class LiuShiActivity extends BaseActivityPresenter<LiuShiActivityPresenter> {
    @Override
    public Class<LiuShiActivityPresenter> getClassDeleGate() {
        return LiuShiActivityPresenter.class;
    }
}
