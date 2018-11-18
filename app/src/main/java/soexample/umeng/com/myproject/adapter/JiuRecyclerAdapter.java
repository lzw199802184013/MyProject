package soexample.umeng.com.myproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.model.JiuBean;

public class JiuRecyclerAdapter extends RecyclerView.Adapter<JiuRecyclerAdapter.MyViewHolder> {
    private List<JiuBean.DataBean> data = new ArrayList<>();
    private Context context;

    public JiuRecyclerAdapter(Context context, List<JiuBean.DataBean> data) {
        this.data = data;
        this.context = context;

    }

    @NonNull
    @Override
    public JiuRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_jiuimage, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JiuRecyclerAdapter.MyViewHolder myViewHolder, int i) {
        Glide.with(context).load(data.get(i).getIcon()).fitCenter().into(myViewHolder.image2);
        myViewHolder.tv_title.setText(data.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image2;
        TextView tv_title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image2 = (ImageView) itemView.findViewById(R.id.image2);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
