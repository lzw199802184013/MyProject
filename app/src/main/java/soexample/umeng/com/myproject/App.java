package soexample.umeng.com.myproject;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import soexample.umeng.com.myproject.model.PubusBeanUtils;
import soexample.umeng.com.myproject.view.PhotoPopwindow;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

    }

}
