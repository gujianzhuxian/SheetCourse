package com.sheetcourse.mobileterminal.fragment.me;

import android.view.View;

//import androidx.fragment.app.Fragment;

import com.sheetcourse.mobileterminal.R;
import com.sheetcourse.mobileterminal.fragment.BaseFragment;
import com.sheetcourse.mobileterminal.utils.ConstantUtils;

import butterknife.ButterKnife;

public class MeFragment extends BaseFragment {
    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_me, null);
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
}
