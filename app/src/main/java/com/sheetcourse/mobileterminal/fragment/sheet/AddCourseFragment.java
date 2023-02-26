package com.sheetcourse.mobileterminal.fragment.sheet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.sheetcourse.mobileterminal.MainActivity;
import com.sheetcourse.mobileterminal.R;
import com.sheetcourse.mobileterminal.application.SYApplication;
import com.sheetcourse.mobileterminal.fragment.BaseFragment;
import com.sheetcourse.mobileterminal.fragment.sheet.util.WeekOfTermSelectDialog;
import com.sheetcourse.mobileterminal.model.MySubject;
import com.sheetcourse.mobileterminal.utils.ConstantUtils;
import com.sheetcourse.mobileterminal.utils.Utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;

public class AddCourseFragment extends BaseFragment {
    public static final String EXTRA_UPDATE_TIMETABLE = "update_timetable";
    private static List<String> sWeekItems;
    private List<String> sStartItems;
    private List<String> sEndItems;

    private TextView mClassNumTextView;//节数文本
    private EditText mNameEditText;//课名文本
    private EditText mClassRoomEditText;//教室文本
    private TextView mWeekOfTermTextView;//周数文本
    private EditText mTeacherEditText;//教师文本
    private OptionsPickerView<String> pvOptions;//选项票据视图(条件选择器)
    private Button saveCourse;
    private MySubject mySubject;


    public static final String EXTRA_COURSE_INDEX = "course_index";//外部课程索引
    public static final String EXTRA_Day_OF_WEEK = "day_of_week";//额外工作日_工作周
    public static final String EXTRA_CLASS_START = "class_start";//类外启动

    /**
     * 保存在MainActivity.WeekOfTerm中的索引值
     */
    private int mIndex;

    private int mClassStart;
    private int mClassEnd;
    private int mDayOfWeek;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_course, container, false);
    }

    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_add_course, null);
        ButterKnife.bind(this, view);
//        setListener();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mySubject=new MySubject();
        mClassNumTextView = view.findViewById(R.id.tv_class_num);
        mNameEditText = view.findViewById(R.id.name_editText);
        mClassRoomEditText = view.findViewById(R.id.et_class_room);
        mWeekOfTermTextView = view.findViewById(R.id.tv_week_of_term);
        mTeacherEditText = view.findViewById(R.id.et_teacher);
        saveCourse = view.findViewById(R.id.save);
        setData();//设置pickerView的数据填充
         //填充关于旗标的文本

        //设置CardView透明度
        setCardViewAlpha(view);
        //设置课表背景

        mClassNumTextView.setOnClickListener(View -> {
            hideInput();//关闭软键盘防止挡住选择控件
            initOptionsPicker();
        });
        mWeekOfTermTextView.setOnClickListener(View -> {
            final WeekOfTermSelectDialog dialog = new WeekOfTermSelectDialog(mActivity, -1);
            dialog.setPositiveBtn(view1 -> {
                mySubject.setWeekOfTerm(dialog.getWeekOfTerm());
                mWeekOfTermTextView.setText(Utils.getFormatStringFromWeekOfTerm(mySubject.getWeekOfTerm()));
                mySubject.setWeekList(Utils.getWeeklist());//将周次列表放入实例
                dialog.dismiss();
            });
            dialog.setNativeBtn(view2 -> dialog.dismiss());
            dialog.show();
        });
        saveCourse.setOnClickListener(View->{
            if(setCourseFromView()){
                Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                transformMysubject();
            }
        });
    }
    /**
     * 通知界面更新
     */
    private void transformMysubject() {
        Intent intent = new Intent(SYApplication.getContext(), MainActivity.class);
        intent.putExtra("Mysubject", mySubject);
        getActivity().startActivity(intent);
    }
    /**
     * 从界面中读取课程信息
     *
     * @return 是否读取成功
     */
    private boolean setCourseFromView() {
        String name = mNameEditText.getText().toString();
        String classroom = mClassRoomEditText.getText().toString();
        String teacher = mTeacherEditText.getText().toString();
        if (name.isEmpty() || classroom.isEmpty() || teacher.isEmpty() ||
                mDayOfWeek == 0 || mClassStart == 0 || mClassEnd == 0) {
            Toast.makeText(getActivity(), "内容不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        int[] colors=new int[]{
                com.sheetcourse.timetableview.R.color.color_1, com.sheetcourse.timetableview.R.color.color_2, com.sheetcourse.timetableview.R.color.color_3, com.sheetcourse.timetableview.R.color.color_4,
                com.sheetcourse.timetableview.R.color.color_5, com.sheetcourse.timetableview.R.color.color_6, com.sheetcourse.timetableview.R.color.color_7, com.sheetcourse.timetableview.R.color.color_8,
                com.sheetcourse.timetableview.R.color.color_9, com.sheetcourse.timetableview.R.color.color_10, com.sheetcourse.timetableview.R.color.color_11, com.sheetcourse.timetableview.R.color.color_31,
                com.sheetcourse.timetableview.R.color.color_32, com.sheetcourse.timetableview.R.color.color_33, com.sheetcourse.timetableview.R.color.color_34, com.sheetcourse.timetableview.R.color.color_35
        };
        Random random=new Random();
        mySubject.setTerm("1");
        mySubject.setName(name);
        mySubject.setRoom(classroom);
        mySubject.setTeacher(teacher);
        mySubject.setStart(mClassStart);
        mySubject.setStep(mClassEnd - mClassStart + 1);
        mySubject.setDay(mDayOfWeek);
        mySubject.setTime("12:00");//无用数据
        mySubject.setColorRandom(colors[random.nextInt(15)]);

        return true;
    }

    public MySubject getMySubject() {
        return mySubject;
    }

    public void setMySubject(MySubject mySubject) {
        this.mySubject = mySubject;
    }

    /**
     * 设置CardView透明度
     */
    private void setCardViewAlpha(View view) {
        CardView cardView = view.findViewById(R.id.cv_edit_1);
        Utils.setCardViewAlpha(cardView);
        cardView = view.findViewById(R.id.cv_edit_2);
        Utils.setCardViewAlpha(cardView);
    }
    /**
     * 初始化节数选择对话框的星期，开始节数，节数节数列表
     */
    private void setData() {
        sWeekItems = new ArrayList<>();

        sWeekItems.add("周一");
        sWeekItems.add("周二");
        sWeekItems.add("周三");
        sWeekItems.add("周四");
        sWeekItems.add("周五");
        sWeekItems.add("周六");
        sWeekItems.add("周日");

        sStartItems = new ArrayList<>();
        sEndItems = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("到");
        for (int i = 1; i <= 12; i++) {
            stringBuilder.append(i);
            sStartItems.add(String.valueOf(i));
            sEndItems.add(stringBuilder.toString());
            stringBuilder.delete(1, stringBuilder.length());
        }
    }

    /**
     * 隐藏键盘
     */
    private void hideInput() {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = mActivity.getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 初始化上课时间选择对话框
     */
    private void initOptionsPicker() {

        int defaultOptions1 = mDayOfWeek - 1;
        int defaultOptions2 = mClassStart - 1;
        int defaultOptions3 = mClassEnd - 1;

        pvOptions = new OptionsPickerBuilder(getActivity(), (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String str12 = sWeekItems.get(options1) + " " + (options2 + 1) + "-" + (options3 + 1) + "节";
            mClassNumTextView.setText(str12);
            //保存节数信息
            mDayOfWeek = options1 + 1;
            mClassStart = options2 + 1;
            mClassEnd = options3 + 1;

        }).setOptionsSelectChangeListener((options1, options2, options3) -> {
            String str1 = sWeekItems.get(options1) + " " + (options2 + 1) + "-" + (options3 + 1) + "节";

            pvOptions.setTitleText(str1);
            if (options3 < options2) {
                pvOptions.setSelectOptions(options1, options2, options2 + 1);
            }
        }).build();

        if (pvOptions != null) {
            pvOptions.setNPicker(sWeekItems, sStartItems, sEndItems);
            pvOptions.setSelectOptions(defaultOptions1, defaultOptions2, defaultOptions3);
            pvOptions.setTitleText("选择上课节数");
            pvOptions.show();
        }

    }

}