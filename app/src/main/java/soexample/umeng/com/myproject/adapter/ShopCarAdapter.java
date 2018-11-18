package soexample.umeng.com.myproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.model.ShopCarBean;

public class ShopCarAdapter extends RecyclerView.Adapter<ShopCarAdapter.MyViewHolder> {
    private List<ShopCarBean.DataBean> data1 = new ArrayList<>();
    private Context context;
    private OnItemClickListener listener;
    private ShopCarsAdapter shopCarsAdapter;

    public ShopCarAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ShopCarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_shop, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ShopCarAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_title7.setText(data1.get(i).getSellerName());
        myViewHolder.cb_check.setChecked(data1.get(i).isBool());
        createRecyclerView9(myViewHolder.recyclerView9, data1.get(i).getList());
        myViewHolder.cb_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data1.get(i).isBool()){
                    data1.get(i).setBool(false);
                }else{
                    data1.get(i).setBool(true);
                }

                List<ShopCarBean.DataBean.ListBean> list = data1.get(i).getList();
                for (int c = 0; c < list.size(); c++) {
                    if (list.get(c).isCheck()) {
                        list.get(c).setCheck(false);
                    } else {
                        list.get(c).setCheck(true);
                    }
                }
                listener.itemClick(data1);
                notifyItemChanged(i);
            }
        });

    }

    private void createRecyclerView9(RecyclerView recyclerView9, List<ShopCarBean.DataBean.ListBean> list) {
        shopCarsAdapter = new ShopCarsAdapter(context, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView9.setLayoutManager(linearLayoutManager);
        recyclerView9.setAdapter(shopCarsAdapter);
        //接口回调
        shopCarsAdapter.setOnItemClickListener(new ShopCarsAdapter.OnItemClickListener() {
            @Override
            public void itemClick() {
                listener.itemClick(data1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public void setList(List<ShopCarBean.DataBean> list) {
        this.data1 = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title7;
        RecyclerView recyclerView9;
        CheckBox cb_check;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title7 = (TextView) itemView.findViewById(R.id.tv_title7);
            recyclerView9 = (RecyclerView) itemView.findViewById(R.id.recyclerView9);
            cb_check = (CheckBox) itemView.findViewById(R.id.cb_check);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

    public interface OnItemClickListener {
        void itemClick(List<ShopCarBean.DataBean> data1);
    }
}
