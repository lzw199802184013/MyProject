package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.LeiBieActivity;
import soexample.umeng.com.myproject.adapter.LeiBieAdapter;
import soexample.umeng.com.myproject.listener.HttpListener;
import soexample.umeng.com.myproject.model.LeiBieBean;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.net.HelperUtils;
import soexample.umeng.com.myproject.net.Http;

public class LeiBieActivityPresenter extends ADeleGate {
    private Context context;
    private RecyclerView recyclerView11;
    private String et;
    private List<LeiBieBean.DataBean> data1 = new ArrayList<>();
    private LeiBieAdapter leiBieAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_leibie;
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = ((LeiBieActivity) context).getIntent();
        et = intent.getStringExtra("et");
        toast(context, et + "");
        recyclerView11 = (RecyclerView) get(R.id.recyclerView11);
        doGet();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView11.setLayoutManager(linearLayoutManager);
        leiBieAdapter = new LeiBieAdapter(context);
        recyclerView11.setAdapter(leiBieAdapter);
    }

    private void doGet() {
        Map<String, String> map = new HashMap<>();
        map.put("keywords", et);
        new HelperUtils().get(Http.ShopGuan_URL, map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                LeiBieBean leiBieBean = gson.fromJson(data, LeiBieBean.class);
                data1 = leiBieBean.getData();
                leiBieAdapter.setList(data1);
            }

            @Override
            public void fail(String error) {

            }
        });
    }


    @Override
    public void getContext(Context context) {
        this.context = context;
    }
}
