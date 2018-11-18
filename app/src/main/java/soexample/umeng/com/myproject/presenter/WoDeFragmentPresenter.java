package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.LoginActivity;
import soexample.umeng.com.myproject.adapter.WodeAdapter;
import soexample.umeng.com.myproject.model.WoDeBean;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.utils.SharedPreferencesUtils;

public class WoDeFragmentPresenter extends ADeleGate implements View.OnClickListener {
    private Context context;
    private RecyclerView recuyclerView7;
    private List<WoDeBean> woDeBeans = new ArrayList<>();
    private ImageView iv;
    private TextView login;

    @Override
    public int getLayoutId() {
        return R.layout.layout_wode;
    }

    @Override
    public void initData() {
        super.initData();
        setData();
        recuyclerView7 = (RecyclerView) get(R.id.recyclerView7);
        WodeAdapter wodeAdapter = new WodeAdapter(context, woDeBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recuyclerView7.setLayoutManager(linearLayoutManager);
        recuyclerView7.setAdapter(wodeAdapter);
        setClick(this, R.id.user_to, R.id.tuichu);
        iv = (ImageView) get(R.id.iv);
        login = (TextView) get(R.id.login);
    }

    private void setData() {
        WoDeBean woDeBean = new WoDeBean();
        woDeBean.setImage(R.drawable.car);
        woDeBean.setName("物流");
        woDeBeans.add(woDeBean);
        woDeBean = new WoDeBean();
        woDeBean.setImage(R.drawable.order);
        woDeBean.setName("订单");
        woDeBeans.add(woDeBean);
        woDeBean = new WoDeBean();
        woDeBean.setImage(R.drawable.quan);
        woDeBean.setName("优惠券");
        woDeBeans.add(woDeBean);
        woDeBean = new WoDeBean();
        woDeBean.setImage(R.drawable.shared);
        woDeBean.setName("分享");
        woDeBeans.add(woDeBean);
        woDeBean = new WoDeBean();
        woDeBean.setImage(R.drawable.feed_back);
        woDeBean.setName("反馈");
        woDeBeans.add(woDeBean);
        woDeBean = new WoDeBean();
        woDeBean.setImage(R.drawable.setting);
        woDeBean.setName("设置");
        woDeBeans.add(woDeBean);

    }


    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_to:
                //没有登🦌点击跳转到登录页面，登录过就跳转到信息页面
                String userName = SharedPreferencesUtils.getString(context, "userName");
                if (TextUtils.isEmpty(userName)) {
                    //如果name为空，跳转到登陆页面
                    context.startActivity(new Intent(context, LoginActivity.class));
                } else {
                    //详情页面
                    toast(context, "已经登录过，如需重新登录，请先退出登录");
                    return;
                }
                break;
            case R.id.tuichu:
                SharedPreferencesUtils.putString(context, "userName", "");
                SharedPreferencesUtils.putString(context, "token", "");
                SharedPreferencesUtils.putString(context, "uid", "");
                SharedPreferencesUtils.putString(context,"ss","2");
                login.setText("登录");
                toast(context, "退出成功");
                SharedPreferencesUtils.putString(context,"cars_refresh","2");
                break;

        }
    }

    public void onResume() {
        String userName = SharedPreferencesUtils.getString(context, "userName");
        String icon = SharedPreferencesUtils.getString(context, "icon");
        if (!TextUtils.isEmpty(userName)) {
            login.setText(userName);
        }
        if (!TextUtils.isEmpty(icon)) {
            Glide.with(context).load(icon).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).fitCenter().into(iv);

        } else {
            iv.setImageResource(R.mipmap.ic_launcher);

        }

    }
}
