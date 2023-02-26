package com.sheetcourse.mobileterminal.network;

import com.sheetcourse.mobileterminal.pojo.Status;
import com.sheetcourse.mobileterminal.pojo.UserData;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

//接口类名：可自定义，尽量和这类请求的含义相关
public interface retrofitNet {

    @GET("user/login")
    Call<Status> ToLogin(@Query("username") String username, @Query("password") String password);


    @GET("user/register")
    Call<Status> ToRegister(@Query("id") Integer id,
                            @Query("username") String username,
                            @Query("password") String password,
                            @Query("identify") String identify);

    @GET("user/updatePsd")
    Call<Status> resetPsd(@Query("username") String username,@Query("newPsd") String newPsd);

    @GET("sms")
    Call<Status> sms(@Query("username") String username);

    @GET("getSms")
    Call<Status> getSms(@Query("username") String username);

    //后续可以增加其他的接口，一个接口对应一个api请求
}

//函数名：可自定义，需要能识别出该接口的作用，该interface里可以增加多个不同的函数
//@GET 注解：用于指定该接口的相对路径，并采用Get方法发起请求
//@Path 注解：需要外部调用时，传入一个uid，该uid会替换@GET注解里相对路径的{uid}
//返回值Call<ResponseBody>，这里用ResponseBody，我们可以直接拿到请求的String内容
//如果要自动转为Model类，例如User,这里直接替换为User就好。

