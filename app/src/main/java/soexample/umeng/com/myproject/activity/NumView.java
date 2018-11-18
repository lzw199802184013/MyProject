package soexample.umeng.com.myproject.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import soexample.umeng.com.myproject.R;

public class NumView extends RelativeLayout {

    private ImageView jian;
    private EditText text;
    private ImageView add;

    public NumView(Context context) {
        super(context);
        init(context);
    }

    public NumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        View view = View.inflate(context, R.layout.num_view, null);
        add = view.findViewById(R.id.add_car);
        text = view.findViewById(R.id.edit_shop_car);
        jian = view.findViewById(R.id.jian_car);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (listener != null) {
                    if (!TextUtils.isEmpty(s)) {
                        int i = Integer.parseInt(s);
                        listener.setNumview(i);
                    }

                }
            }
        });
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                data++;
                listener.setNumview(data);
            }
        });
        jian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data > 1) {
                    data--;
                    listener.setNumview(data);
                } else {
                    Toast.makeText(context, "最少有一件商品", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addView(view);
    }

    private int data;
    public void setData(int data) {
        this.data = data;
        text.setText(data + "");
    }

    //接口回调
    private NumListener listener;

    public void setNum(NumListener listener) {
        this.listener = listener;
    }

    public interface NumListener {
        void setNumview(int num);
}
}
