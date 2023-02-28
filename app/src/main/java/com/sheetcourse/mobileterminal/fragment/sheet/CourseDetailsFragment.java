package com.sheetcourse.mobileterminal.fragment.sheet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sheetcourse.mobileterminal.R;
import com.sheetcourse.mobileterminal.activity.ClickButtonActivity;
import com.sheetcourse.mobileterminal.activity.CourseActivity;
import com.sheetcourse.mobileterminal.fragment.BaseFragment;
import com.sheetcourse.mobileterminal.fragment.FragmentFactory;
import com.sheetcourse.mobileterminal.model.MySubject;
import com.sheetcourse.mobileterminal.utils.ConstantUtils;
import com.sheetcourse.mobileterminal.utils.IndirectClass;
import com.sheetcourse.mobileterminal.utils.Utils;
import com.sheetcourse.timetableview.model.Schedule;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class CourseDetailsFragment extends BaseFragment {
    private List<Schedule> schedulelist;
    private static final String[] aStrWeek = new String[]{
            "周一", "周二", "周三", "周四", "周五", "周六", "周日"
    };

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
            schedulelist= (List<Schedule>) getArguments().getSerializable("beans");
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
        Button editButton = view.findViewById(R.id.btn_edit);
        Button enterButton=view.findViewById(R.id.enter_course);
        setCourseTextView(view);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(CourseDetailsActivity.this, EditActivity.class);
//                intent.putExtra(EditActivity.EXTRA_COURSE_INDEX, mIndex);
//                startActivityForResult(intent, EDIT_ID);
                IndirectClass indirect= ClickButtonActivity.indirectClass;
                ClickButtonActivity activity = (ClickButtonActivity) indirect.getActivity();
//                https://blog.csdn.net/lgm870490308/article/details/99826673
                Fragment fragment = new AddCourseFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("schedule",schedulelist.get(0));
                fragment.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_click_button, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), CourseActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 初始化TextView
     */
    private void setCourseTextView(View view) {
        Schedule schedule=schedulelist.get(0);
        TextView name = view.findViewById(R.id.tv_class_name);
        name.setText(schedule.getName());
        TextView room = view.findViewById(R.id.tv_class_room);
        room.setText(schedule.getRoom());
        TextView classNum = view.findViewById(R.id.tv_class_num);
//        classNum.setText(schedule.getStart());
        int class_start = schedule.getStart();
        int class_num = schedule.getStep();
        classNum.setText(String.format(getString(R.string.schedule_section),
                aStrWeek[schedule.getDay() - 1], class_start, (class_start + class_num - 1)));
        TextView weekTerm = view.findViewById(R.id.tv_week_of_term);
        weekTerm.setText(String.format(getString(R.string.week_of_term_format),
                schedule.getWeekList().toString(), "周"));
//        int weekofitem=getWeekOfTermFromWeeklist(schedule.getWeekList());
//        System.out.println(schedule.getWeekList());
//        weekTerm.setText(Utils.getFormatStringFromWeekOfTerm(weekofitem));
        TextView teacher = view.findViewById(R.id.tv_teacher);
        teacher.setText(schedule.getTeacher());
    }
    private static int getWeekOfTermFromWeeklist(List<Integer> weeklist){
        int weekOfTerm = 0;
        List<Boolean> list=new ArrayList<>(20);
        for(int i=0;i<list.size();i++){
            if(i==(weeklist.get(i) -1)){
                list.set(i,true);
            }else {list.set(i,false);}
        }
        for (int i = 0, len = list.size(); i < len; i++) {
            if (list.get(i)) {
                //Log.d("weekofterm",String.valueOf(i));
                weekOfTerm++;
            }
            if (i != len - 1) {//最后不移动
                weekOfTerm = weekOfTerm << 1;
            }
        }
        System.out.println(weekOfTerm);
        return weekOfTerm;
    }
}
