package com.sheetcourse.mobileterminal.fragment.plan;

import android.os.Bundle;
import android.view.View;


import com.sheetcourse.mobileterminal.R;
import com.sheetcourse.mobileterminal.fragment.BaseFragment;
import com.sheetcourse.mobileterminal.utils.StartUtils;


public class PlanFragment extends BaseFragment {

    @Override
    protected View getSuccessView() {
        return null;
    }

    @Override
    protected Object requestData() {
        return null;
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        StartUtils.startActivityById(mActivity, R.id.top1);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
