package com.sheetcourse.mobileterminal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.sheetcourse.mobileterminal.activity.ClickButtonActivity;
import com.sheetcourse.mobileterminal.application.SYApplication;


/**
 * Created by zhaoshuo on 2016/3/17.
 */
public class StartUtils {
    public static void startActivityById(Context context, int resId,String title){
        Intent intent = new Intent(SYApplication.getContext(), ClickButtonActivity.class);
        intent.putExtra("resId",resId);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }
    public static void startActivityByIdForResult(Fragment activity, int resId , int requestCode,String title){
        Intent intent = new Intent(SYApplication.getContext(), ClickButtonActivity.class);
        intent.putExtra("resId",resId);
        intent.putExtra("title",title);
        activity.startActivityForResult(intent,requestCode);
    }
}
