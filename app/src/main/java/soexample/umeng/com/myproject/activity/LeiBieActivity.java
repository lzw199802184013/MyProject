package soexample.umeng.com.myproject.activity;

import soexample.umeng.com.myproject.mvp.presenter.BaseActivityPresenter;
import soexample.umeng.com.myproject.presenter.LeiBieActivityPresenter;

public class LeiBieActivity extends BaseActivityPresenter<LeiBieActivityPresenter> {
    @Override
    public Class<LeiBieActivityPresenter> getClassDeleGate() {
        return LeiBieActivityPresenter.class;
    }
}
