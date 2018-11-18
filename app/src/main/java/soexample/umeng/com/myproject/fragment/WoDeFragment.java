package soexample.umeng.com.myproject.fragment;

import soexample.umeng.com.myproject.mvp.presenter.BaseFragmentPresenter;
import soexample.umeng.com.myproject.presenter.ShouYeFragmentPresenter;
import soexample.umeng.com.myproject.presenter.WoDeFragmentPresenter;

public class WoDeFragment extends BaseFragmentPresenter<WoDeFragmentPresenter> {

    @Override
    public Class<WoDeFragmentPresenter> getClassDeleGate() {
        return WoDeFragmentPresenter.class;
    }

    @Override
    public void onResume() {
        super.onResume();
        deleGate.onResume();
    }
}
