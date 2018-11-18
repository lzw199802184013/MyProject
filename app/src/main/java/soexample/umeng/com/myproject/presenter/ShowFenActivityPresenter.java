package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.ShowFenyeActivity;
import soexample.umeng.com.myproject.adapter.FenYeAdapter;
import soexample.umeng.com.myproject.listener.HttpListener;
import soexample.umeng.com.myproject.model.FenYeBean;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.net.HelperUtils;
import soexample.umeng.com.myproject.net.Http;

public class ShowFenActivityPresenter extends ADeleGate {
    private Context context;
    private RecyclerView recyclerView10;
    private String pscid;
    private List<FenYeBean.DataBean> data1 = new ArrayList<>();
    private FenYeAdapter fenYeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shoufen;
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = ((ShowFenyeActivity) context).getIntent();
        pscid = intent.getStringExtra("pscid");
//        toast(context, pscid + "");
        recyclerView10 = (RecyclerView) get(R.id.recyclerView10);
        doGet();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView10.setLayoutManager(linearLayoutManager);
        fenYeAdapter = new FenYeAdapter(context);
        recyclerView10.setAdapter(fenYeAdapter);
    }

    private void doGet() {
        Map<String, String> map = new HashMap<>();
        map.put("pscid", pscid);
        new HelperUtils().get(Http.ShopFen_URL, map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                FenYeBean fenYeBean = gson.fromJson(data, FenYeBean.class);
                data1 = fenYeBean.getData();
                fenYeAdapter.setList(data1);

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
