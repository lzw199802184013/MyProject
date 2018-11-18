package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.adapter.ShopCarAdapter;
import soexample.umeng.com.myproject.listener.HttpListener;
import soexample.umeng.com.myproject.model.DeleteBean;
import soexample.umeng.com.myproject.model.ShopCarBean;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.net.HelperUtils;
import soexample.umeng.com.myproject.net.Http;
import soexample.umeng.com.myproject.net.HttpUtils;
import soexample.umeng.com.myproject.utils.SharedPreferencesUtils;

public class ShopCarFragmentPresenter extends ADeleGate implements View.OnClickListener {
    private Context context;
    private RecyclerView recyclerView8;
    private ShopCarAdapter shopCarAdapter;
    private List<ShopCarBean.DataBean> data1 = new ArrayList<>();
    private CheckBox iv_circle;
    private TextView tv_price;
    private TextView go;
    private TextView bianji;

    private RelativeLayout delete_layout;
    //全选 ，价格，数量
    private boolean isClick = true;//定义一个开关用来全选和不选
    private boolean isopen = true;
    private String uid;
    private String token;

    @Override
    public int getLayoutId() {
        return R.layout.layout_shopcar;
    }

    @Override
    public void initData() {
        super.initData();
        recyclerView8 = (RecyclerView) get(R.id.recyclerView8);
        doGets();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView8.setLayoutManager(linearLayoutManager);
        shopCarAdapter = new ShopCarAdapter(context);
        recyclerView8.setAdapter(shopCarAdapter);
        setClick(this, R.id.layout_all, R.id.bianji, R.id.iv_circle, R.id.go, R.id.delete_layout);
        iv_circle = (CheckBox) get(R.id.iv_circle);
        tv_price = (TextView) get(R.id.tv_price);
        bianji = (TextView) get(R.id.bianji);
        go = (TextView) get(R.id.go);
        delete_layout = (RelativeLayout) get(R.id.delete_layout);
        //接口回调
        shopCarAdapter.setOnItemClickListener(new ShopCarAdapter.OnItemClickListener() {
            @Override
            public void itemClick(List<ShopCarBean.DataBean> data) {
                //删除
                data1 = data;
                double allPrice = 0;
                int num = 0;//选中的个数
                int numAll = 0;//所有的个数
                int num1 = 0;//类型个数
                int num2 = 0;//商品选中的个数
                for (int a = 0; a < data1.size(); a++) {
                    List<ShopCarBean.DataBean.ListBean> list = data1.get(a).getList();
                    num2 = 0;
                    for (int b = 0; b < list.size(); b++) {
                        numAll++;//全部的个数
                        if (list.get(b).isCheck()) {
                            num1++;//
                            num2++;
                            allPrice += list.get(b).getPrice() * list.get(b).getNum();
                            num += list.get(b).getNum();//选中的个数
                        }

                    }
                    //商品个数选中等于商家
                    if (list.size() == num2) {
                        data1.get(a).setBool(true);
                    } else {
                        data1.get(a).setBool(false);
                    }

                }
                //如果选中的个数小于所有的个数就改变全选状态值
                if (num1 < numAll) {
//                    iv_circle.setImageResource(R.drawable.cricle_no);
                    iv_circle.setChecked(false);
                    isClick = true;//状态值改变
                } else {
//                    iv_circle.setImageResource(R.drawable.cricle_yes);
                    iv_circle.setChecked(true);
                    isClick = false;
                }

                go.setText("去结算(" + num + ")");
                tv_price.setText("合计:" + allPrice);
                shopCarAdapter.setList(data1);

            }
        });
    }

    //获取商品
    private void doGets() {
        uid = SharedPreferencesUtils.getString(context, "uid");
        token = SharedPreferencesUtils.getString(context, "token");
        if (TextUtils.isEmpty(uid)) {
            return;
        }

        new HttpUtils().get(Http.ShopShow_URL + "?uid=" + uid + "&token=" + token).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.equals("null")) {
                    return;
                }
                Gson gson = new Gson();
                ShopCarBean shopCarBean = gson.fromJson(data, ShopCarBean.class);
                data1 = shopCarBean.getData();
                shopCarAdapter.setList(data1);
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

    //刷新购物车
    public void onResume() {
        String ss = SharedPreferencesUtils.getString(context, "cars_refresh");
        String success = SharedPreferencesUtils.getString(context, "success");
        if ("0".equals(ss) || "3".equals(ss)) {//登录或者从添加购物车过过来
            doGets();
            SharedPreferencesUtils.putString(context, "cars_refresh", "1");
        } else if ("2".equals(ss)) {//退出登陆
            Log.i("ShopCarFragm", "=====");
            data1.clear();
            if (shopCarAdapter != null) {
                shopCarAdapter.setList(data1);
            }
            SharedPreferencesUtils.putString(context, "cars_refresh", "1");
        }

        if ("4".equals(success)) {
            String uid = SharedPreferencesUtils.getString(context, "uid");
            String token = SharedPreferencesUtils.getString(context, "token");
            doGets();
            SharedPreferencesUtils.putString(context, "success", "5");
        }


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_circle:
                if (isClick) {
                    allGoods(true);
                    isClick = false;
                } else {
                    allGoods(false);
                    isClick = true;

                }
                break;
            case R.id.bianji://编辑
                if (isopen) {
                    bianji.setText("完成");
                    tv_price.setVisibility(View.GONE);
                    delete_layout.setVisibility(View.VISIBLE);
                    isopen = false;

                } else {
                    bianji.setText("编辑");
                    tv_price.setVisibility(View.VISIBLE);
                    tv_price.setText("合计：0.00");
                    go.setText("去结算(0)");
                    delete_layout.setVisibility(View.GONE);
                    isopen = true;
                }

                break;
            case R.id.delete_layout:
                int temp = 0;
                for (int i = 0; i < data1.size(); i++) {
                    List<ShopCarBean.DataBean.ListBean> list = data1.get(i).getList();

                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).isCheck()) {
                            temp++;
                            String pid = list.get(j).getPid();
                            toast(context, pid + "");
                            doGes(pid);
                            list.remove(j);
                        }
                        if (list.size() == 0) {
                            //删除商家信息以及商品信息
                            data1.remove(i);
                        }
                    }
                }

                if (temp == 0) {
                    toast(context, "起码选中一件商品");
                    return;
                }

                break;
        }
    }

    private void doGes(String pid) {
    /*    uid = SharedPreferencesUtils.getString(context, "uid");
        token = SharedPreferencesUtils.getString(context, "token");*/
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("pid", pid);
        map.put("token", token);
        new HelperUtils().get(Http.ShopDelete_URL, map).result(new HttpListener() {
            @Override
            public void success(String data) {
                shopCarAdapter.notifyDataSetChanged();
                Gson gson = new Gson();
                DeleteBean deleteBean = gson.fromJson(data, DeleteBean.class);
                if (deleteBean.getCode().equals("0")) {
                    doGets();
                }

            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //全选
    private void allGoods(boolean bool) {
        double allPrice = 0;//总价格
        int num = 0;//去结算的数量
        for (int a = 0; a < data1.size(); a++) {
            data1.get(a).setBool(bool);
            List<ShopCarBean.DataBean.ListBean> list = data1.get(a).getList();
            for (int b = 0; b < list.size(); b++) {
                list.get(b).setCheck(bool);//选中
                allPrice = allPrice + list.get(b).getPrice() * list.get(b).getNum();//价格
                num += list.get(b).getNum();//数量
            }

        }
        if (bool) {
            iv_circle.setChecked(true);
//            iv_circle.setImageResource(R.drawable.cricle_yes);
            tv_price.setText("合计:" + allPrice);
            go.setText("去结算(" + num + ")");
        } else {
            iv_circle.setChecked(false);
//            iv_circle.setImageResource(R.drawable.cricle_no);
            tv_price.setText("合计:0.00");
            go.setText("去结算(0)");
        }
        shopCarAdapter.notifyDataSetChanged();
    }
}
