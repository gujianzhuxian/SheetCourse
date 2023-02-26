package com.sheetcourse.mobileterminal.fragment.sheet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sheetcourse.mobileterminal.R;
import com.sheetcourse.mobileterminal.fragment.BaseFragment;
import com.sheetcourse.mobileterminal.utils.ConstantUtils;
import com.sheetcourse.timetableview.model.Schedule;

import java.util.List;

import butterknife.ButterKnife;

public class CourseDetailsFragment extends BaseFragment {

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_course_details, null);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            List<Schedule> list= (List<Schedule>) getArguments().getSerializable("beans");
            for(int i=0;i<list.size();i++)
                System.out.println(list.get(i).getWeekList());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_details, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = view.findViewById(R.id.btn_edit);

    }
    /**
     * 初始化TextView
     */
    private void setCourseTextView(View view) {
        
    }

}