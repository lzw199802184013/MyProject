package soexample.umeng.com.myproject.listener;

public interface HttpListener {
    void success(String data);

    void fail(String error);
}
