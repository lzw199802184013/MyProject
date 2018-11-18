package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.adapter.FenLeiAdapter;
import soexample.umeng.com.myproject.adapter.FenLeisAdapter;
import soexample.umeng.com.myproject.listener.HttpListener;
import soexample.umeng.com.myproject.model.FenLeiBean;
import soexample.umeng.com.myproject.model.FenLeisBean;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.net.Http;
import soexample.umeng.com.myproject.net.HttpUtils;

public class FenLeiFragmentPresenter extends ADeleGate {
    private Context context;
    private RecyclerView recyclerView4, recyclerView5;
    private FenLeiAdapter fenLeiAdapter;
    private List<FenLeiBean.DataBean> data1 = new ArrayList<>();
    private FenLeisAdapter fenLeisAdapter;
    private List<FenLeisBean.DataBean> data2 = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.layout_fenlei;
    }

    @Override
    public void initData() {
        super.initData();
        recyclerView4 = (RecyclerView) get(R.id.recyclerView4);
        doFen();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView4.setLayoutManager(linearLayoutManager);
        doFens(1);
        recyclerView5 = (RecyclerView) get(R.id.recyclerView5);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView5.setLayoutManager(linearLayoutManager1);
    }


    //分类左
    private void doFen() {

        new HttpUtils().get(Http.FENLEI_URL).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                FenLeiBean fenLeiBean = gson.fromJson(data, FenLeiBean.class);
                data1 = fenLeiBean.getData();
                fenLeiAdapter = new FenLeiAdapter(context);
                data1.get(0).setClick(true);
                fenLeiAdapter.setList(data1);
                recyclerView4.setAdapter(fenLeiAdapter);
                //回调
                fenLeiAdapter.setOnClick(new FenLeiAdapter.OnClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        int cid = data1.get(position).getCid();
                        doFens(cid);
                        //改变状态
                        for (int i = 0; i < data1.size(); i++) {
                            if (position == i) {
                                data1.get(i).setClick(true);
                            } else {
                                data1.get(i).setClick(false);
                            }

                        }
                        fenLeiAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //右边分类
    private void doFens(int cid) {
        new HttpUtils().get(Http.FENLEIS_URL + cid).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                FenLeisBean fenLeisBean = gson.fromJson(data, FenLeisBean.class);
                data2 = fenLeisBean.getData();
                fenLeisAdapter = new FenLeisAdapter(context);
                fenLeisAdapter.setList(data2);
                recyclerView5.setAdapter(fenLeisAdapter);
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
