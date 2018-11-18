package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;

public class LeiBieActivityPresenter extends ADeleGate {
    private Context context;
    private RecyclerView recyclerView11;

    @Override
    public int getLayoutId() {
        return R.layout.activity_leibie;
    }

    @Override
    public void initData() {
        super.initData();
        recyclerView11 = (RecyclerView) get(R.id.recyclerView11);
        doGet();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView11.setLayoutManager(linearLayoutManager);
    }

    private void doGet() {

    }


    @Override
    public void getContext(Context context) {
        this.context = context;
    }
}
