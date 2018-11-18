package soexample.umeng.com.myproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.ShowActivity;
import soexample.umeng.com.myproject.model.PubuBean;

public class PubuChildAdapter extends RecyclerView.Adapter<PubuChildAdapter.MyViewHolder> {
    private List<PubuBean.DataBean.ListBean> list;
    private Context context;

    public PubuChildAdapter(List<PubuBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PubuChildAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_pubuc, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull PubuChildAdapter.MyViewHolder myViewHolder, final int i) {
        String images = list.get(i).getImages();
        String[] split = images.split("[|]");
        myViewHolder.sdView.setImageURI(split[0]);
        myViewHolder.tv_title3.setText(list.get(i).getTitle());
        myViewHolder.tv_price.setText("￥:" + list.get(i).getPrice());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String detailUrl = list.get(i).getDetailUrl();
                Intent intent = new Intent(context, ShowActivity.class);
                intent.putExtra("url", detailUrl);
                intent.putExtra("pid", list.get(i).getPid());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdView;
        TextView tv_title3, tv_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sdView = (SimpleDraweeView) itemView.findViewById(R.id.sdView);
            tv_title3 = (TextView) itemView.findViewById(R.id.tv_title3);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}
