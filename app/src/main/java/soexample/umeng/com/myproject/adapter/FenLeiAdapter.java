package soexample.umeng.com.myproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.model.FenLeiBean;

public class FenLeiAdapter extends RecyclerView.Adapter<FenLeiAdapter.MyViewHolder> {
    private List<FenLeiBean.DataBean> data1 = new ArrayList<>();
    private Context context;

    public FenLeiAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FenLeiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = View.inflate(context, R.layout.layout_fenlei1, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FenLeiAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_title4.setText(data1.get(i).getName());
        myViewHolder.tv_title4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(i);
            }
        });
        //点击
        if (data1.get(i).isClick()){
            myViewHolder.relativelayout.setBackgroundColor(Color.parseColor("#d43c3c"));
            myViewHolder.tv_title4.setTextColor(Color.parseColor("#ffffff"));
        }else{
            myViewHolder.relativelayout.setBackgroundColor(Color.parseColor("#ffffff"));
            myViewHolder.tv_title4.setTextColor(Color.parseColor("#222222"));
        }
    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public void setList(List<FenLeiBean.DataBean> list) {
        this.data1 = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title4;
        RelativeLayout relativelayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title4 = (TextView) itemView.findViewById(R.id.tv_title4);
             relativelayout = (RelativeLayout) itemView.findViewById(R.id.relativelayout);
        }
    }

    //接口回调
    private  OnClickListener listener;
    public  void  setOnClick(OnClickListener listener){
            this.listener=listener;

    }
    public interface OnClickListener {
        void onItemClick(int position);

    }

}
