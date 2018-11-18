package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.MainActivity;
import soexample.umeng.com.myproject.activity.StartActivity;
import soexample.umeng.com.myproject.activity.WelcomeActivity;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.utils.UltimateBar;

public class WelcomeActivityPresenter extends ADeleGate {
    private SharedPreferences sp;
    private ViewPager view_vp;
    private LinearLayout linearlayout;
    private int[] image = {
            R.drawable.welcome1_top,
            R.drawable.welcome2_top,
            R.drawable.welcome3_top
    };
    private int[] image2 = {
            R.drawable.welcome1_buttom,
            R.drawable.welcome2_buttom,
            R.drawable.welcome3_bottom_bg

    };
    private Button click;

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initData() {
        super.initData();
        sp = ((WelcomeActivity) context).getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putBoolean("isfirst", true).commit();
        view_vp = (ViewPager) get(R.id.view_vp);
        linearlayout = (LinearLayout) get(R.id.linearlayout);
        click = (Button) get(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MainActivity.class));
                ((WelcomeActivity) context).finish();
            }
        });
        myAdapter = new MyAdapter();
        view_vp.setAdapter(myAdapter);
        view_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                int item = view_vp.getCurrentItem();
                if (item == 2) {
                    click.setVisibility(View.VISIBLE);
                } else {
                    click.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int i) {
                setPoint(i % image.length);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setPoint(0);
    }

    public void setPoint(int position) {
        linearlayout.removeAllViews();
        for (int a = 0; a < image2.length; a++) {
            ImageView view = new ImageView(((WelcomeActivity) context));
            if (position == a) {
                view.setBackgroundResource(R.drawable.circle);
            } else {
                view.setBackgroundResource(R.drawable.circle2);
            }
            linearlayout.addView(view);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.width = 15;
            params.height = 15;
            params.leftMargin = 5;
            view.setLayoutParams(params);
        }

    }

    private Context context;

    @Override
    public void getContext(Context context) {
        this.context = context;
    }


    private MyAdapter myAdapter;

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return image.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = View.inflate(context, R.layout.layout_image, null);
            ImageView img1 = (ImageView) view.findViewById(R.id.img1);
            img1.setBackgroundResource(image[position]);
            ImageView img2 = (ImageView) view.findViewById(R.id.img2);
            img2.setBackgroundResource(image2[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
