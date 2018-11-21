package soexample.umeng.com.myproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.ShowActivity;
import soexample.umeng.com.myproject.model.LeiBieBean;

public class LeiBieAdapter extends RecyclerView.Adapter<LeiBieAdapter.MyViewHolder> {
    private Context context;
    private List<LeiBieBean.DataBean> data1 = new ArrayList<>();

    public LeiBieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LeiBieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_leibie, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeiBieAdapter.MyViewHolder myViewHolder, final int i) {
        String images = data1.get(i).getImages();
        String[] split = images.split("[|]");
        myViewHolder.sdView4.setImageURI(split[0]);
        myViewHolder.tv_title9.setText(data1.get(i).getTitle());
        myViewHolder.tv_price3.setText("￥:" + data1.get(i).getPrice());
        //跳转
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowActivity.class);
                intent.putExtra("list2", data1.get(i));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public void setList(List<LeiBieBean.DataBean> list) {
        this.data1 = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdView4;
        TextView tv_title9, tv_price3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sdView4 = (SimpleDraweeView) itemView.findViewById(R.id.sdView4);
            tv_title9 = (TextView) itemView.findViewById(R.id.tv_title9);
            tv_price3 = (TextView) itemView.findViewById(R.id.tv_price3);
        }
    }
}
