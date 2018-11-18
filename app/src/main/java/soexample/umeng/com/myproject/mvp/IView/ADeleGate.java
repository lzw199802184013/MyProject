package soexample.umeng.com.myproject.mvp.IView;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class ADeleGate implements DeleGate {
    private View rootView;
    @Override
    public void initData() {

    }

    private SparseArray<View> views = new SparseArray<>();

    public <T extends View> T get(int id) {
        T view = (T) views.get(id);
        if (view == null) {
            view = rootView.findViewById(id);
            views.put(id, view);

        }
        return view;
    }

    public void setClick(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(listener);
        }
    }

    @Override
    public void create(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        rootView = inflater.inflate(getLayoutId(), viewGroup, false);
    }

    @Override
    public View rootView() {
        return rootView;
    }

    //toast方法
    public void toast(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    private Context context;

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    public abstract int getLayoutId();
}
