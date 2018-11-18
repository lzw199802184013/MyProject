package soexample.umeng.com.myproject.activity;

import soexample.umeng.com.myproject.mvp.presenter.BaseActivityPresenter;
import soexample.umeng.com.myproject.presenter.MainActivityPresenter;

public class MainActivity extends BaseActivityPresenter<MainActivityPresenter> {


    @Override
    public Class<MainActivityPresenter> getClassDeleGate() {
        return MainActivityPresenter.class;
    }

    @Override
    protected void onResume() {
        super.onResume();
        deleGate.onResume();
    }
}
