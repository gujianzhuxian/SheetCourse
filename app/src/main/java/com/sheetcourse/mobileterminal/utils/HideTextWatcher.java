package com.sheetcourse.mobileterminal.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

// 定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
public class HideTextWatcher implements TextWatcher {
    private EditText mView;
    private int mMaxLength;
    private Activity activit;

    public HideTextWatcher(EditText v, int maxLength,Activity activit) {
        this.mView = v;
        this.mMaxLength = maxLength;
        this.activit=activit;
    }
    public static void hideOneInputMethod(Activity act, View v) {
        // 从系统服务中获取输入法管理器
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 关闭屏幕上的输入法软键盘
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length() == mMaxLength) {
            // 隐藏输入法软键盘
            hideOneInputMethod(activit, mView);
        }
    }
}