package soexample.umeng.com.myproject.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import soexample.umeng.com.myproject.activity.StartActivity;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.utils.UltimateBar;

public abstract class BaseActivityPresenter<T extends ADeleGate> extends AppCompatActivity {

    public T deleGate;

    public abstract Class<T> getClassDeleGate();

    public BaseActivityPresenter() {
        try {
            deleGate = getClassDeleGate().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleGate.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(deleGate.rootView());
        deleGate.getContext(this);
        deleGate.initData();
        //设置沉浸式
        UltimateBar.newImmersionBuilder().applyNav(false)//是否应用到导航栏
                .build(this)
                .apply();
    }
}
