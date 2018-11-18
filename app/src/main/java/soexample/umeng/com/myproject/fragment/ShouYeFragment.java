package soexample.umeng.com.myproject.fragment;

import android.animation.AnimatorSet;

import java.util.concurrent.CompletableFuture;

import soexample.umeng.com.myproject.mvp.presenter.BaseFragmentPresenter;
import soexample.umeng.com.myproject.presenter.ShouYeFragmentPresenter;

public class ShouYeFragment extends BaseFragmentPresenter<ShouYeFragmentPresenter> {


    @Override
    public Class<ShouYeFragmentPresenter> getClassDeleGate() {
        return ShouYeFragmentPresenter.class;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

}
