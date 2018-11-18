package soexample.umeng.com.myproject.mvp.presenter;

import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import soexample.umeng.com.myproject.mvp.IView.ADeleGate;

public abstract class BaseFragmentPresenter<T extends ADeleGate> extends Fragment {

    public T deleGate;
    private AnimatorSet animatorSet;
    public abstract Class<T> getClassDeleGate();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            deleGate = getClassDeleGate().newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        deleGate.create(inflater, container, savedInstanceState);
        return deleGate.rootView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        deleGate.getContext(getActivity());
        deleGate.initData();

    }
}
