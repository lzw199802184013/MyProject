package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.myproject.R;
import soexample.umeng.com.myproject.activity.LeiBieActivity;
import soexample.umeng.com.myproject.activity.LiuShi;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;

public class LiuShiActivityPresenter extends ADeleGate implements View.OnClickListener {
    private Context context;
    private EditText et_1;
    private LiuShi liushi;
    private List<String> stringList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_liushi;
    }

    @Override
    public void initData() {
        super.initData();
        et_1 = (EditText) get(R.id.et_1);
        liushi = (LiuShi) get(R.id.liushi);
        setClick(this, R.id.sousu);
//        stringList.add("杀杀杀");
//        liushi.setData(stringList);
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sousu:
                String et1 = et_1.getText().toString().trim();
                if (TextUtils.isEmpty(et1)) {
                    toast(context, "关键字不能为空哦");
                    return;
                }

                stringList.add(0, et1);
                liushi.setData(stringList);
                //
                Intent intent = new Intent(context, LeiBieActivity.class);
                intent.putExtra("et", et1);
                context.startActivity(intent);
                break;
        }
    }
}
