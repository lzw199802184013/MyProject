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
import soexample.umeng.com.myproject.activity.ShowFenyeActivity;
import soexample.umeng.com.myproject.model.FenYeBean;

public class FenYeAdapter extends RecyclerView.Adapter<FenYeAdapter.MyViewHolder> {
    private List<FenYeBean.DataBean> data1 = new ArrayList<>();
    private Context context;

    public FenYeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FenYeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_fenye, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FenYeAdapter.MyViewHolder myViewHolder, final int i) {
        String images = data1.get(i).getImages();
        String[] split = images.split("[|]");
        myViewHolder.sdView3.setImageURI(split[0]);
        myViewHolder.tv_title8.setText(data1.get(i).getTitle());
        myViewHolder.tv_price2.setText("￥：" + data1.get(i).getPrice() + "");
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowActivity.class);
                intent.putExtra("list", data1.get(i));
                context.startActivity(intent);
                ((ShowFenyeActivity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public void setList(List<FenYeBean.DataBean> list) {
        this.data1 = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdView3;
        TextView tv_title8, tv_price2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sdView3 = (SimpleDraweeView) itemView.findViewById(R.id.sdView3);
            tv_title8 = (TextView) itemView.findViewById(R.id.tv_title8);
            tv_price2 = (TextView) itemView.findViewById(R.id.tv_price2);
        }
    }
}
