package soexample.umeng.com.myproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.model.FenLeisBean;

public class FenLeisAdapter extends RecyclerView.Adapter<FenLeisAdapter.MyViewHolder> {
    private List<FenLeisBean.DataBean> data2 = new ArrayList<>();
    private Context context;

    public FenLeisAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FenLeisAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_fenlei2, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FenLeisAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_title5.setText(data2.get(i).getName());
        createRecyclerView6(myViewHolder.recyclerView6, data2.get(i).getList());
    }

    private void createRecyclerView6(RecyclerView recyclerView6, List<FenLeisBean.DataBean.ListBean> list) {
        FenLeissAdapter fenLeissAdapter = new FenLeissAdapter(context, list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView6.setLayoutManager(gridLayoutManager);
        recyclerView6.setAdapter(fenLeissAdapter);
    }

    @Override
    public int getItemCount() {
        return data2.size();
    }

    public void setList(List<FenLeisBean.DataBean> list) {
        this.data2 = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title5;
        RecyclerView recyclerView6;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title5 = (TextView) itemView.findViewById(R.id.tv_title5);
            recyclerView6 = (RecyclerView) itemView.findViewById(R.id.recyclerView6);
        }
    }
}
