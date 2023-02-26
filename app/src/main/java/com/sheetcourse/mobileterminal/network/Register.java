package com.sheetcourse.mobileterminal.network;

import com.sheetcourse.mobileterminal.pojo.Status;
import com.sheetcourse.mobileterminal.pojo.UserData;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register {
    private static final String TAG = "LoginActivity";

    private OkHttpClient okHttpClient;
    //创建Retrofit实例
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.2.5:8080/")
            //.client(okHttpClient)
            //设置数据解析器
            //.addConverterFactory(new NullOnEmptyConverterFactory()) //必须是要第一个
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public String UserRegister(UserData userData) throws Exception{
        //创建Service实例
        retrofitNet service = retrofit.create(retrofitNet.class);
        //对发送请求进行封装
        Call<Status> dataCall = service.ToRegister(userData.getId(),
                userData.getUsername(),userData.getPassword(), userData.getUserIdentify());
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
            return "网络请求返回为空，稍等试试";
        }
        return body.getInfo();
    }
}
