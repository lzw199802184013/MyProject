package soexample.umeng.com.myproject.activity;

import android.content.Intent;
import android.support.annotation.Nullable;

import soexample.umeng.com.myproject.mvp.presenter.BaseActivityPresenter;
import soexample.umeng.com.myproject.presenter.PhotoActivityPresenter;

public class PhotoActivity extends BaseActivityPresenter<PhotoActivityPresenter> {
    @Override
    public Class<PhotoActivityPresenter> getClassDeleGate() {
        return PhotoActivityPresenter.class;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        deleGate.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        deleGate.onResume();
    }
}
