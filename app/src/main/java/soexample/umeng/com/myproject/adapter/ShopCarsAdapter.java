package soexample.umeng.com.myproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.NumView;
import soexample.umeng.com.myproject.listener.HttpListener;
import soexample.umeng.com.myproject.model.ShopCarBean;
import soexample.umeng.com.myproject.net.HttpUtils;
import soexample.umeng.com.myproject.utils.SharedPreferencesUtils;

public class ShopCarsAdapter extends RecyclerView.Adapter<ShopCarsAdapter.MyViewHolder> {
    private Context context;
    private List<ShopCarBean.DataBean.ListBean> list;
    private OnItemClickListener listener;

    public ShopCarsAdapter(Context context, List<ShopCarBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ShopCarsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_shop2, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopCarsAdapter.MyViewHolder myViewHolder, final int i) {
        String images = list.get(i).getImages();
        String[] split = images.split("[|]");
        myViewHolder.car_image.setImageURI(split[0]);
        myViewHolder.car_title.setText(list.get(i).getTitle());
        myViewHolder.car_price.setText("￥：" + list.get(i).getPrice());
        //全选全不选
        if (list.get(i).isCheck()) {
//            myViewHolder.car_cicrle.setImageResource(R.drawable.cricle_yes);
            myViewHolder.car_circle.setChecked(true);
        } else {
//            myViewHolder.car_cicrle.setImageResource(R.drawable.cricle_no);
            myViewHolder.car_circle.setChecked(false);
        }
        //圆圈点击事件
        myViewHolder.car_circle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (list.get(i).isCheck()) {
                    list.get(i).setCheck(false);
                } else {
                    list.get(i).setCheck(true);
                }
                //单条目刷新
                notifyItemChanged(i);
                listener.itemClick();
            }
        });
//        myViewHolder.car_cicrle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (list.get(i).isCheck()) {
//                    list.get(i).setCheck(false);
//                } else {
//                    list.get(i).setCheck(true);
//                }
//                //单条目刷新
//                notifyItemChanged(i);
//                listener.itemClick();
//            }
//        });
        //加减
        myViewHolder.numView.setData(list.get(i).getNum());
        myViewHolder.numView.setNum(new NumView.NumListener() {
            @Override
            public void setNumview(int num) {
                list.get(i).setNum(num);
                updateNum(i);
                listener.itemClick();
            }
        });
    }
    //更新购物车
    private void updateNum(int i) {
        int num = list.get(i).getNum();
        String pid = list.get(i).getPid();
        int selected = list.get(i).getSelected();
        int sellerid = list.get(i).getSellerid();
        String uid = SharedPreferencesUtils.getString(context, "uid");
        String token = SharedPreferencesUtils.getString(context, "token");
        new HttpUtils().get("http://www.zhaoapi.cn/product/updateCarts?uid=" + uid + "&sellerid=" + sellerid + "&pid=" + pid + "&selected=" + selected + "&num=" + num + "&token=" + token).result(new HttpListener() {
            @Override
            public void success(String data) {
            }

            @Override
            public void fail(String error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView car_image;
        TextView car_title;
        TextView car_price;
        NumView numView;
        CheckBox car_circle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           car_circle = (CheckBox) itemView.findViewById(R.id.car_cicrle);
            car_image = (SimpleDraweeView) itemView.findViewById(R.id.car_image);
            car_title = (TextView) itemView.findViewById(R.id.car_title);
            car_price = (TextView) itemView.findViewById(R.id.car_price);
            numView = itemView.findViewById(R.id.numview);
        }
    }

    //接口回调


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

    public interface OnItemClickListener {
        void itemClick();
    }
}
