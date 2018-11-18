package soexample.umeng.com.myproject.fragment;

import soexample.umeng.com.myproject.mvp.presenter.BaseFragmentPresenter;
import soexample.umeng.com.myproject.presenter.FenLeiFragmentPresenter;
import soexample.umeng.com.myproject.presenter.ShouYeFragmentPresenter;

public class FenLeiFragment extends BaseFragmentPresenter<FenLeiFragmentPresenter> {

    @Override
    public Class<FenLeiFragmentPresenter> getClassDeleGate() {
        return FenLeiFragmentPresenter.class;
    }
}
