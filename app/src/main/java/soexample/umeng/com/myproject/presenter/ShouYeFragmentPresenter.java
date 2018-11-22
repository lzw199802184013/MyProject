package soexample.umeng.com.myproject.presenter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.android.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.LiuShiActivity;
import soexample.umeng.com.myproject.activity.MainActivity;
import soexample.umeng.com.myproject.adapter.JiuAdapter;
import soexample.umeng.com.myproject.adapter.PubuAdapter;
import soexample.umeng.com.myproject.listener.BaseAnimatorListener;
import soexample.umeng.com.myproject.listener.HttpListener;
import soexample.umeng.com.myproject.model.JiuBean;
import soexample.umeng.com.myproject.model.LunBoBean;
import soexample.umeng.com.myproject.model.PubuBean;
import soexample.umeng.com.myproject.model.PubusBean;
import soexample.umeng.com.myproject.model.PubusBeanUtils;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.net.Http;
import soexample.umeng.com.myproject.net.HttpUtils;
import soexample.umeng.com.myproject.utils.NetworkUtils;

public class ShouYeFragmentPresenter extends ADeleGate implements View.OnClickListener {
    private Context context;
    private BGABanner bgaBanner;
    private List<LunBoBean.DataBean> data1 = new ArrayList<>();
    private List<String> dataTitle = new ArrayList<>();
    private ViewPager view_vp2;
    private int allPager = 0;//总页数
    private List<JiuBean.DataBean> data2 = new ArrayList<>();
    private List<JiuBean.DataBean> data3 = new ArrayList<>();
    private LinearLayout linear_layout2;
    private RecyclerView recyclerView2;
    private List<PubuBean.DataBean> data4 = new ArrayList<>();
    private PubuAdapter pubuAdapter;
    private TextView tv_paoma;
    private String[] mTitle = {
            "锄禾日当午",
            "汗滴如下土",
            "谁知盘中餐",
            "粒粒皆辛苦"

    };
    private int position = 0;
    private AnimatorSet animatorSet;

    @Override
    public int getLayoutId() {
        return R.layout.layout_shouye;
    }

    @Override
    public void initData() {
        super.initData();
        linear_layout2 = (LinearLayout) get(R.id.linearlayout2);
        view_vp2 = (ViewPager) get(R.id.view_vp2);
        doJiu();
        view_vp2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setPoint(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        bgaBanner = (BGABanner) get(R.id.bgaBanner);
        doBgaBanner();
        recyclerView2 = (RecyclerView) get(R.id.recyclerView2);
        doRecyclerView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(linearLayoutManager);
        //注册
        PubusBeanUtils.getPubusBeanUtils().init(context);
        //判断网络状态用于缓存
        if (NetworkUtils.isConnected(context)) {
            doRecyclerView();
        } else {
            String json = PubusBeanUtils.getPubusBeanUtils().query().get(0).getJson();
            getString(json);
            pubuAdapter = new PubuAdapter(context, data4);
            recyclerView2.setAdapter(pubuAdapter);
        }
        tv_paoma = (TextView) get(R.id.tv_paoma);
        tv_paoma.setText(mTitle[position]);
        setAnimation(tv_paoma);
        setClick(this, R.id.shou_scan, R.id.tv_1);
    }


    //轮播图
    private void doBgaBanner() {
        new HttpUtils().get(Http.BANNER_URL).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                LunBoBean lunBoBean = gson.fromJson(data, LunBoBean.class);
                data1 = lunBoBean.getData();
                for (int a = 0; a < data1.size(); a++) {
                    String title = data1.get(a).getTitle();
                    dataTitle.add(title);

                }
                bgaBanner.setAdapter(new BGABanner.Adapter() {
                    @Override
                    public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                        String icon = data1.get(position).getIcon();
                        String replace = icon.replace("https", "http");
                        Glide.with(context).load(replace).fitCenter().into((ImageView) itemView);
                    }
                });
                bgaBanner.setData(data1, dataTitle);
            }

            @Override
            public void fail(String error) {

            }
        });

    }

    //九宫格
    private void doJiu() {
        new HttpUtils().get(Http.JIU_URL).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                JiuBean jiuBean = gson.fromJson(data, JiuBean.class);
                int size = jiuBean.getData().size();
                int num = size / 10;
                int num2 = size % 10;
                if (num2 != 0) {
                    num = num + 1;//页数
                }
                allPager = num;
                for (int b = 0; b < jiuBean.getData().size(); b++) {
                    JiuBean.DataBean dataBean = jiuBean.getData().get(b);
                    if (b < 10) {
                        data2.add(dataBean);
                    } else {
                        data3.add(dataBean);
                    }
                }
                JiuAdapter jiuAdapter = new JiuAdapter(context, allPager, data2, data3);
                view_vp2.setAdapter(jiuAdapter);
                setPoint(0);

            }

            @Override
            public void fail(String error) {

            }
        });
    }

    //九宫格小圆点
    private void setPoint(int position) {
        linear_layout2.removeAllViews();
        for (int c = 0; c < allPager; c++) {
            ImageView imageView = new ImageView(context);
            if (position == c) {
                imageView.setBackgroundResource(R.drawable.circle);
            } else {
                imageView.setBackgroundResource(R.drawable.circle2);
            }
            linear_layout2.addView(imageView);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            params.leftMargin = 5;
            params.width = 15;
            params.height = 15;
            imageView.setLayoutParams(params);
        }
    }

    //瀑布流
    private void doRecyclerView() {
        new HttpUtils().get(Http.PUBU_URL).result(new HttpListener() {
            @Override
            public void success(String data) {
                if (data.contains("<")) {
                    return;
                }
                getString(data);
                PubusBeanUtils.getPubusBeanUtils().delete();
                PubusBean pubusBean = new PubusBean();
                pubusBean.setJson(data);
                PubusBeanUtils.getPubusBeanUtils().insert(pubusBean);
                pubuAdapter = new PubuAdapter(context, data4);
                recyclerView2.setAdapter(pubuAdapter);
            }

            @Override
            public void fail(String error) {

            }
        });

    }

    private void getString(String data) {
        Gson gson = new Gson();
        PubuBean pubuBean = gson.fromJson(data, PubuBean.class);
        data4 = pubuBean.getData();
        data4.remove(0);
    }


    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    public void setAnimation(TextView view) {
        ObjectAnimator objectAnimatorTop = ObjectAnimator.ofFloat(view, "translationY", 0, -50);
        ObjectAnimator objectAnimatorTopB = ObjectAnimator.ofFloat(view, "translationY", 50, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", 0, 0);
        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(objectAnimator, objectAnimatorTop, objectAnimatorTopB);//顺序播放
        animatorSet.setDuration(2000);
        animatorSet.start();
        objectAnimatorTop.addListener(new BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                position++;
                tv_paoma.setText(mTitle[position % mTitle.length]);
            }
        });
        animatorSet.addListener(new BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                animatorSet.setDuration(2000);
                animatorSet.start();
            }
        });

    }

    //二维码
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shou_scan:
                scan();
                break;
            case R.id.tv_1:
                context.startActivity(new Intent(context, LiuShiActivity.class));
                break;
        }
    }

    //跳转扫一扫
    private void scan() {

        PermissionUtils.permission(context, new PermissionUtils.PermissionListener() {
            @Override
            public void success() {
                Intent intent = new Intent(context, CaptureActivity.class);
                context.startActivity(intent);
            }
        });
    }

}
