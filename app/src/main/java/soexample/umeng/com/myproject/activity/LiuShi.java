package soexample.umeng.com.myproject.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import soexample.umeng.com.myproject.R;

public class LiuShi extends RelativeLayout {
    private LinearLayout linear_layout;

    public LiuShi(Context context) {
        super(context);
        init(context);
    }

    public LiuShi(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LiuShi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Context context;

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.layout_ver, null);
        //垂直布局的id
        linear_layout = view.findViewById(R.id.linear_layout);
        //添加到view
        addView(view);
    }

    //设置数据
    private LinearLayout view1;
    private int position = 0;

    public void setData(final List<String> stringList) {
        linear_layout.removeAllViews();
        view1 = (LinearLayout) View.inflate(context, R.layout.layout_hor, null);
        //添加到垂直布局
        linear_layout.addView(view1);
        //定义一个变量
        int len = 0;
        int size=0;
        view1.removeAllViews();
        for (int a = 0; a < stringList.size(); a++) {
            String s = stringList.get(a);
            len += s.length();
            size++;
            if (len > 18||size>5) {
                view1 = (LinearLayout) View.inflate(context, R.layout.layout_hor, null);
                linear_layout.addView(view1);
                len = 0;
                size=0;
            }
            //textview布局
            final View viewText = View.inflate(context, R.layout.layout_context, null);
            //text view的id
            TextView tv_text = (TextView) viewText.findViewById(R.id.tv_text);
            //设置输入的值
            tv_text.setText(s);
            //添加到view
            view1.addView(viewText);
            //设置宽高
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewText.getLayoutParams();
            params.weight = 1;
            params.leftMargin = 10;
            params.rightMargin = 10;
            params.topMargin = 10;
            viewText.setLayoutParams(params);
            //长按删除
            tv_text.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    view1.removeView(viewText);
                    stringList.remove(position);
                    setData(stringList);
                    return true;
                }
            });
        }

    }
}
