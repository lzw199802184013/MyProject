package soexample.umeng.com.myproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.ShowFenyeActivity;
import soexample.umeng.com.myproject.model.FenLeisBean;

public class FenLeissAdapter extends RecyclerView.Adapter<FenLeissAdapter.MyViewHolder> {
    private List<FenLeisBean.DataBean.ListBean> list;
    private Context context;

    public FenLeissAdapter(Context context, List<FenLeisBean.DataBean.ListBean> list) {
        this.list = list;
        this.context = context;

    }


    @NonNull
    @Override
    public FenLeissAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_fenlei3, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FenLeissAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.sdView2.setImageURI(list.get(i).getIcon());
        myViewHolder.tv_title6.setText(list.get(i).getName());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowFenyeActivity.class);
                intent.putExtra("pscid", list.get(i).getPscid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdView2;
        TextView tv_title6;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sdView2 = (SimpleDraweeView) itemView.findViewById(R.id.sdView2);
            tv_title6 = (TextView) itemView.findViewById(R.id.tv_title6);
        }
    }
}
