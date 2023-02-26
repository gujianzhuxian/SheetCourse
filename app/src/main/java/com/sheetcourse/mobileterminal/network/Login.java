package com.sheetcourse.mobileterminal.network;
//Retrofit同步与异步https://blog.csdn.net/xdy1120/article/details/90127522

import android.util.Log;
import android.util.StateSet;

import com.google.gson.Gson;
import com.sheetcourse.mobileterminal.pojo.Status;
import com.sheetcourse.mobileterminal.pojo.UserData;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit2.Call;
import retrofit2.Retrofit;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login{
    private static final String TAG = "LoginActivity";
    private String jsonString;
    private String resetState;
    private String verifycode;
    private String getcerifycode;

    //创建Retrofit实例
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.2.5:8080/")
            //.client(OkHttpHolder.OK_HTTP_CLIENT)
            //设置数据解析器
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public Status userLogin(String username,String password) throws Exception{
        //创建Service实例
        retrofitNet service = retrofit.create(retrofitNet.class);
        //对发送请求进行封装
        Call<Status> dataCall = service.ToLogin(username,password);
        //异步请求 并不适合函数调用
//        dataCall.enqueue(new Callback<Status>() {
//            //请求成功回调
//            @Override
//            public void onResponse(Call<Status> call, Response<Status> response) {
//                Status body=response.body();
//                if(body==null){return;}
//                jsonString=body.getInfo().toString();
//                System.out.println(jsonString+"   "+body);
//                if(jsonString!=null) return;
//            }
//            //请求失败回调
//            @Override
//            public void onFailure(Call<Status> call, Throwable t) {
//                Log.e(TAG,"回调失败：" + t.getMessage() + "," + t.toString());
//            }
//        });
        //同步请求
        Response<Status> data= dataCall.execute();
        Status body=data.body();
        if(body==null){
            Status status=new Status();
            status.setInfo("网络请求失败");
            return status;
        }
        return body;
    }
    public String ResetPsd(String username,String newPsd) throws Exception{
        resetState="网络请求失败";
        //创建Service实例
        retrofitNet service = retrofit.create(retrofitNet.class);
        //对发送请求进行封装
        Call<Status> dataCall = service.resetPsd(username,newPsd);
        //异步请求 并不适合函数调用
//        dataCall.enqueue(new Callback<Status>() {
//            //请求成功回调
//            @Override
//            public void onResponse(Call<Status> call, Response<Status> response) {
//                Status body=response.body();
//                if(body==null){return;}
//                resetState=body.getInfo();
//                System.out.println(resetState+"   "+body);
//                if(resetState!=null) return;
//            }
//            //请求失败回调
//            @Override
//            public void onFailure(Call<Status> call, Throwable t) {
//                Log.e(TAG,"回调失败：" + t.getMessage() + "," + t.toString());
//            }
//        });
        //同步请求
        Response<Status> data= dataCall.execute();
        Status body=data.body();
        if(body==null){
            return "网络请求失败";
        }
        resetState=body.getInfo();
        return resetState;
    }
    public String getVerifycode(String username) throws Exception{
        getcerifycode ="网络请求失败";
        //创建Service实例
        retrofitNet service = retrofit.create(retrofitNet.class);
        //对发送请求进行封装
        Call<Status> dataCall = service.sms(username);
        //异步请求 并不适合函数调用
//        dataCall.enqueue(new Callback<Status>() {
//            //请求成功回调
//            @Override
//            public void onResponse(Call<Status> call, Response<Status> response) {
//                Status body=response.body();
//                if(body==null){return;}
//                getcerifycode=body.getInfo();
//                System.out.println(getcerifycode+"   "+body);
//                if(getcerifycode!=null) return;
//            }
//            //请求失败回调
//            @Override
//            public void onFailure(Call<Status> call, Throwable t) {
//                Log.e(TAG,"回调失败：" + t.getMessage() + "," + t.toString());
//            }
//        });
        //同步请求
        Response<Status> data= dataCall.execute();
        Status body=data.body();
        if(body==null){
            return "网络请求失败";
        }
        getcerifycode=body.getInfo();
        return getcerifycode;
    }
}
