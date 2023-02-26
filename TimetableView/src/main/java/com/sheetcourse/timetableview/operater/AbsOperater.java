package com.sheetcourse.timetableview.operater;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.sheetcourse.timetableview.TimetableView;

/**
 * 这个功能也真的很有用，因为每个人需求都有一些不同，所以一个控件肯定不能满足每个人的需求，在这种情况下，你需要自定义实现逻辑，v2.0.6 以及以上支持该功能
 * 抽象的业务逻辑
 * Created by Liu ZhuangFei on 2018/9/2.
 */
public abstract class AbsOperater {
    public void init(Context context, AttributeSet attrs, TimetableView view){};

    public void showView(){};

    public void updateDateView(){};

    public void updateSlideView(){};

    public void changeWeek(int week, boolean isCurWeek){};

    public LinearLayout getFlagLayout(){return null;};

    public LinearLayout getDateLayout(){return null;};

    public void setWeekendsVisiable(boolean isShow){};
}
