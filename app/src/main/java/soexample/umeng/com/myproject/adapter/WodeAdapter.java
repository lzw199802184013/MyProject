package soexample.umeng.com.myproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.model.WoDeBean;

public class WodeAdapter extends RecyclerView.Adapter<WodeAdapter.MyViewHolder> {
    private Context context;
    private List<WoDeBean> woDeBeans;

    public WodeAdapter(Context context, List<WoDeBean> woDeBeans) {
        this.context = context;
        this.woDeBeans = woDeBeans;

    }

    @NonNull
    @Override
    public WodeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.wode_adapter, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WodeAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.account_left_image.setImageResource(woDeBeans.get(i).getImage());
        myViewHolder.account_title.setText(woDeBeans.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return woDeBeans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView account_left_image;
        TextView account_title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            account_left_image = (ImageView) itemView.findViewById(R.id.account_left_image);
            account_title = (TextView) itemView.findViewById(R.id.account_title);
        }
    }
}
