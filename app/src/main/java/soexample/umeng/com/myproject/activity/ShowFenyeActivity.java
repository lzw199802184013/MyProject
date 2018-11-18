package soexample.umeng.com.myproject.activity;

import soexample.umeng.com.myproject.mvp.presenter.BaseActivityPresenter;
import soexample.umeng.com.myproject.presenter.ShowFenActivityPresenter;

public class ShowFenyeActivity extends BaseActivityPresenter<ShowFenActivityPresenter> {
    @Override
    public Class<ShowFenActivityPresenter> getClassDeleGate() {
        return ShowFenActivityPresenter.class;
    }
}
