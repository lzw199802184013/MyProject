package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.MainActivity;
import soexample.umeng.com.myproject.activity.StartActivity;
import soexample.umeng.com.myproject.activity.WelcomeActivity;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.utils.UltimateBar;

public class StartActivityPresenter extends ADeleGate implements View.OnClickListener {
    private TextView tv_time;
    private int second = 3;
    private SharedPreferences sp;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 101) {
                second--;
                tv_time.setText(second + "s");
                boolean b = sp.getBoolean("isfirst", false);
                if (second == 0) {
                    if (!b) {
                        ((StartActivity) context).startActivity(new Intent(context, WelcomeActivity.class));
                        handler.removeCallbacksAndMessages(null);
                        ((StartActivity) context).finish();

                    } else {
                        ((StartActivity) context).startActivity(new Intent(context, MainActivity.class));
                        handler.removeCallbacksAndMessages(null);
                        ((StartActivity) context).finish();

                    }
                } else {
                    handler.sendEmptyMessageDelayed(101, 1000);
                }


            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    public void initData() {
        super.initData();
        tv_time = (TextView) get(R.id.tv_time);
        setClick(this, R.id.jump);
        sp = ((StartActivity) context).getSharedPreferences("config", Context.MODE_PRIVATE);
        handler.sendEmptyMessageDelayed(101, 1000);

    }

    private Context context;

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jump:
                boolean b = sp.getBoolean("isfirst", false);
                if (!b) {
                    ((StartActivity) context).startActivity(new Intent(context, WelcomeActivity.class));
                    handler.removeCallbacksAndMessages(null);
                    ((StartActivity) context).finish();

                } else {
                    ((StartActivity) context).startActivity(new Intent(context, MainActivity.class));
                    handler.removeCallbacksAndMessages(null);
                    ((StartActivity) context).finish();

                }
                break;
        }
    }
}
