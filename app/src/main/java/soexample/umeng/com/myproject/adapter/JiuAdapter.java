package soexample.umeng.com.myproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.model.JiuBean;

public class JiuAdapter extends PagerAdapter {
    private Context context;
    private int allPager;
    private List<JiuBean.DataBean> data2 = new ArrayList<>();
    private List<JiuBean.DataBean> data3 = new ArrayList<>();
    public JiuAdapter(Context context, int allPager, List<JiuBean.DataBean> data2, List<JiuBean.DataBean> data3) {
        this.context = context;
        this.allPager = allPager;
        this.data2 = data2;
        this.data3=data3;
    }

    @Override
    public int getCount() {
        return allPager;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.layout_jiugongge, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        createRecyclerView(recyclerView,position);
        container.addView(view);
        return view;
    }

    //设置recyclerview的适配器
    private void createRecyclerView(RecyclerView recyclerView,int position ) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        JiuRecyclerAdapter jiuRecyclerAdapter = null;
        if (position==0){
                jiuRecyclerAdapter= new JiuRecyclerAdapter(context,data2);
        }else {
            jiuRecyclerAdapter=new JiuRecyclerAdapter(context,data3);
        }
        recyclerView.setAdapter(jiuRecyclerAdapter);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
