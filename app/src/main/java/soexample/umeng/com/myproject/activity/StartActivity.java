package soexample.umeng.com.myproject.activity;

import soexample.umeng.com.myproject.mvp.presenter.BaseActivityPresenter;
import soexample.umeng.com.myproject.presenter.StartActivityPresenter;

public class StartActivity  extends BaseActivityPresenter<StartActivityPresenter>{
    @Override
    public Class<StartActivityPresenter> getClassDeleGate() {
        return StartActivityPresenter.class;
    }
}
