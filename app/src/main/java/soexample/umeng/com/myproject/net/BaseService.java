package soexample.umeng.com.myproject.net;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface BaseService {
    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> map);
    @POST
    Observable<ResponseBody> post(@Url String url, @QueryMap Map<String, String> map);
    @Multipart
    @POST("/file/upload")
    Observable<ResponseBody> upload(@Query("uid") String uid, @Part MultipartBody.Part part);
}
