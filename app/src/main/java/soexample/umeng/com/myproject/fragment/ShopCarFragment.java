package soexample.umeng.com.myproject.fragment;

import soexample.umeng.com.myproject.mvp.presenter.BaseFragmentPresenter;
import soexample.umeng.com.myproject.presenter.ShopCarFragmentPresenter;
import soexample.umeng.com.myproject.presenter.ShouYeFragmentPresenter;

public class ShopCarFragment extends BaseFragmentPresenter<ShopCarFragmentPresenter> {

    @Override
    public Class<ShopCarFragmentPresenter> getClassDeleGate() {
        return ShopCarFragmentPresenter.class;
    }

    @Override
    public void onResume() {
        super.onResume();
        deleGate.onResume();
    }

    public void onRefresh() {
        if(deleGate!=null){
            deleGate.onResume();
        }

    }
}
