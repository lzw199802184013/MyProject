package soexample.umeng.com.myproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.model.PubuBean;

public class PubuAdapter extends RecyclerView.Adapter<PubuAdapter.MyViewHolder> {
    private Context context;
    private List<PubuBean.DataBean> data4;

    public PubuAdapter(Context context, List<PubuBean.DataBean> data4) {
        this.context = context;
        this.data4 = data4;
    }

    @NonNull
    @Override
    public PubuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_pubu, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PubuAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title2.setText(data4.get(i).getSellerName());
        createRecyclerView(myViewHolder.recyclerView3,data4.get(i).getList());
    }

    private void createRecyclerView(RecyclerView recyclerView3, List<PubuBean.DataBean.ListBean> list) {
        PubuChildAdapter pubuChildAdapter = new PubuChildAdapter(list,context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(staggeredGridLayoutManager);
        recyclerView3.setAdapter(pubuChildAdapter);
    }


    @Override
    public int getItemCount() {
        return data4.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title2;
        RecyclerView recyclerView3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title2 = (TextView) itemView.findViewById(R.id.tv_title2);
            recyclerView3 = (RecyclerView) itemView.findViewById(R.id.recyclerView3);
        }
    }
}
