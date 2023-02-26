package com.sheetcourse.mobileterminal.utils;

import android.app.Activity;
import android.content.Context;

import com.sheetcourse.mobileterminal.MainActivity;

public class IndirectClass {
    private Context contxt;
    private Activity activity;

    public Context getContxt() {
        return contxt;
    }

    public void setContxt(Context contxt) {
        this.contxt = contxt;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity =activity;
    }

    public IndirectClass(Context context, Activity activity) {
        this.setContxt(context);
        this.setActivity(activity);
    }
}
