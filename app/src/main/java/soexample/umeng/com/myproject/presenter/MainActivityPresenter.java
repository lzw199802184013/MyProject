package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.MainActivity;
import soexample.umeng.com.myproject.fragment.FenLeiFragment;
import soexample.umeng.com.myproject.fragment.ShopCarFragment;
import soexample.umeng.com.myproject.fragment.ShouYeFragment;
import soexample.umeng.com.myproject.fragment.WoDeFragment;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.utils.SharedPreferencesUtils;
import soexample.umeng.com.myproject.view.TabView;
import soexample.umeng.com.myproject.view.TabViewChild;

public class MainActivityPresenter extends ADeleGate {
    private Context context;
    private TabView tabview;
    private List<TabViewChild> tabViewList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        super.initData();
        tabview = (TabView) get(R.id.tabview);
        TabViewChild tabViewChild1 = new TabViewChild(R.drawable.index_yes, R.drawable.index_no, "首页", new ShouYeFragment());
        TabViewChild tabViewChild2 = new TabViewChild(R.drawable.list_yes, R.drawable.list_no, "分类", new FenLeiFragment());
        TabViewChild tabViewChild3 = new TabViewChild(R.drawable.car_yes, R.drawable.car_no, "购物车", new ShopCarFragment());
        TabViewChild tabViewChild4 = new TabViewChild(R.drawable.me_yes, R.drawable.me_no, "我的", new WoDeFragment());
        tabViewList.add(tabViewChild1);
        tabViewList.add(tabViewChild2);
        tabViewList.add(tabViewChild3);
        tabViewList.add(tabViewChild4);
        tabview.setTabViewChild(tabViewList, ((MainActivity) context).getSupportFragmentManager());
        tabview.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView imageView, TextView textView) {
                if(position==2){
                    ((ShopCarFragment)tabViewList.get(position).getmFragment()).onRefresh();
                }
            }
        });
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }


    public void onResume() {
        String webtype = SharedPreferencesUtils.getString(context, "cars_refresh");
        if ("0".equals(webtype)||"2".equals(webtype)) {
            tabview.setTabViewDefaultPosition(2);
            SharedPreferencesUtils.putString(context, "cars_refresh", "1");
        }

    }
}
