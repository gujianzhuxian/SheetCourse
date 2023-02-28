package com.sheetcourse.mobileterminal.fragment.plan;

import android.os.Bundle;
import android.view.View;


import com.sheetcourse.mobileterminal.R;
import com.sheetcourse.mobileterminal.fragment.BaseFragment;
import com.sheetcourse.mobileterminal.utils.ConstantUtils;
import com.sheetcourse.mobileterminal.utils.StartUtils;

import butterknife.ButterKnife;


public class PlanFragment extends BaseFragment {

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_plan, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected Object requestData() {
        return ConstantUtils.STATE_SUCCESSED;
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
