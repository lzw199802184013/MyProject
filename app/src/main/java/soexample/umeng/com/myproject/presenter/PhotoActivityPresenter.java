package soexample.umeng.com.myproject.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import soexample.umeng.com.myproject.R;

import soexample.umeng.com.myproject.activity.LoginActivity;
import soexample.umeng.com.myproject.activity.PhotoActivity;
import soexample.umeng.com.myproject.listener.HttpListener;
import soexample.umeng.com.myproject.model.LoginBean;
import soexample.umeng.com.myproject.model.UserBean;
import soexample.umeng.com.myproject.mvp.IView.ADeleGate;
import soexample.umeng.com.myproject.net.BaseService;
import soexample.umeng.com.myproject.net.HelperUtils;
import soexample.umeng.com.myproject.net.Http;
import soexample.umeng.com.myproject.net.HttpUtils;
import soexample.umeng.com.myproject.utils.SharedPreferencesUtils;
import soexample.umeng.com.myproject.view.PhotoPopwindow;

import static android.app.Activity.RESULT_OK;

public class PhotoActivityPresenter extends ADeleGate implements View.OnClickListener {
    private Context context;
    private ImageView iv_photo;
    private static String path;//sd路径
    private Bitmap head;//头像Bitmap

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    public void initData() {
        super.initData();
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        iv_photo = (ImageView) get(R.id.iv_photo);
        setClick(this, R.id.iv_photo);
//        File file = new File(Environment.getExternalStorageDirectory(), "head.png");
//        if (file.exists()) {
//            iv_photo.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//        }
    }

    @Override
    public void getContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_photo:
                show();
                break;

        }
    }

    private void show() {
        new PhotoPopwindow(context, get(R.id.layout_parent), new PhotoPopwindow.OnSelectPictureListener() {
            @Override
            public void onTakePhoto() {
                //拍照
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.png")));
                ((PhotoActivity) context).startActivityForResult(intent2, 2);
            }

            @Override
            public void onSelectPicture() {
                //相册选择
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                ((PhotoActivity) context).startActivityForResult(intent1, 1);

            }

            @Override
            public void onCancel() {

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.png");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras == null) {
                        return;
                    }
                    head = extras.getParcelable("data");
                    if (head != null) {
                        iv_photo.setImageBitmap(head);
                        String fileName = path + "/head.png";//图片名字
                        setPicToView(head);//保存在SD卡中
                        File file1 = new File(fileName);
                        uploadPic(file1);
                        //uploadImage(fileName);
                    }
                }
                break;
            default:
                break;
        }
    }

    //Retrofit上传
    private void uploadPic(File file1) {
        final RequestBody file = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "head.png", file);
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.zhaoapi.cn/").build();
        retrofit.create(BaseService.class)
                .upload("22047", part).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Log.i("ResponseBody", responseBody.string());
                        toast(context, "上传成功");
                        dohttp();
                    }
                });
    }


    private void dohttp() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", "22047");
        new HelperUtils().get(Http.ShopUser_URL, map).result(new HttpListener() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                UserBean userBean = gson.fromJson(data, UserBean.class);
                if ("0".equals(userBean.getCode())) {
                    if (userBean.getData().getIcon() != null) {
                        String icon = (String) userBean.getData().getIcon();
                        String replace = icon.replace("https", "http");
                        Glide.with(context).load(replace).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).fitCenter().into(iv_photo);
                        SharedPreferencesUtils.putString(context, "icon", icon);
                    }
                }
            }

            @Override
            public void fail(String error) {

            }
        });

    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 127);
        intent.putExtra("outputY", 127);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", false);//不启用人脸识别
        intent.putExtra("return-data", true);
        ((PhotoActivity) context).startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "/head.png";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public void onResume() {
        String icon = SharedPreferencesUtils.getString(context, "icon");
        if (!TextUtils.isEmpty(icon)) {
            String replace = icon.replace("https", "http");
            Log.i("aaa",replace);
            Glide.with(context).load(replace).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).fitCenter().into(iv_photo);
        } else {
            iv_photo.setImageResource(R.mipmap.ic_launcher);
//                File file = new File(Environment.getExternalStorageDirectory(), "head.png");
//                if (file.exists()) {
//                    iv.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//                }
        }
    }
}
