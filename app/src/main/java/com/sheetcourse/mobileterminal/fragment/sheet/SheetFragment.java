package com.sheetcourse.mobileterminal.fragment.sheet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sheetcourse.mobileterminal.R;
import com.sheetcourse.mobileterminal.activity.ClickButtonActivity;
import com.sheetcourse.mobileterminal.application.SYApplication;
import com.sheetcourse.mobileterminal.fragment.BaseFragment;
import com.sheetcourse.mobileterminal.language.OnEnglishDateBuildAdapter;
import com.sheetcourse.mobileterminal.language.OnEnglishItemBuildAdapter;
import com.sheetcourse.mobileterminal.model.MySubject;
import com.sheetcourse.mobileterminal.model.SubjectRepertory;
import com.sheetcourse.mobileterminal.utils.ConstantUtils;
import com.sheetcourse.mobileterminal.utils.DialogUtils;
import com.sheetcourse.mobileterminal.utils.ExcelUtils;
import com.sheetcourse.mobileterminal.utils.FileUtils;
import com.sheetcourse.mobileterminal.utils.StartUtils;
import com.sheetcourse.mobileterminal.utils.Utils;
import com.sheetcourse.timetableview.TimetableView;
import com.sheetcourse.timetableview.listener.ISchedule;
import com.sheetcourse.timetableview.listener.IWeekView;
import com.sheetcourse.timetableview.listener.OnItemBuildAdapter;
import com.sheetcourse.timetableview.listener.OnSlideBuildAdapter;
import com.sheetcourse.timetableview.model.Schedule;
import com.sheetcourse.timetableview.view.WeekView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class SheetFragment extends BaseFragment implements View.OnClickListener {
    private static final int REQUEST_CODE_COURSE_EDIT = 1;//请求代码请求编辑
    private static final int REQUEST_CODE_FILE_CHOOSE = 2;//请求代码文件选择
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //http://www.tastones.com/stackoverflow/android/butterknife/unbinding_views_in_butterknife/
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private MySubject subject;


    public static final String AD_URL="https://image.baidu.com/search/detail?z=0&word=%E5%9F%8E%E5%B8%82%E5%BB%BA%E7%AD%91%E6%91%84%E5%BD%B1%E4%B8%93%E9%A2%98&hs=0&pn=6&spn=0&di=&pi=3983&rn=&tn=baiduimagedetail&is=&ie=utf-8&oe=utf-8&cs=825057118%2C3516313570&os=&simid=&adpicid=0&lpn=0&fr=albumsdetail&fm=&ic=0&sme=&cg=&bdtype=&oriquery=&objurl=https%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D825057118%2C3516313570%26fm%3D193%26f%3DGIF&fromurl=ipprf_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bev2_z%26e3Bv54AzdH3Fv6jwptejAzdH3Fb8aa0n8cm&gsm=&islist=&querylist=&album_tab=%E5%BB%BA%E7%AD%91&album_id=7";
    private static final String TAG = "onCreate()";
    //控件
    TimetableView mTimetableView;
    WeekView mWeekView;
    Button moreButton;

    LinearLayout layout;
    TextView titleTextView;
    List<MySubject> mySubjects;

    //记录切换的周次，不一定是当前周
    int target = -1;
    AlertDialog alertDialog;


    @Override
    protected View getSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_sheet, null);
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
        switch (view.getId()) {
            case R.id.id_layout:
                //如果周次选择已经显示了，那么将它隐藏，更新课程、日期
                //否则，显示
                if (mWeekView.isShowing()) hideWeekView();
                else showWeekView();
                break;
        }
    }

    public SheetFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ExampleFragment");
        if (getArguments() != null) {
            subject = (MySubject) getArguments().getSerializable("mysubject");//接收AddcourseFragment增添新的课程的实体对象
            mySubjects= (List<MySubject>) getArguments().getSerializable("mysubjects");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: SheetFragment");
        return inflater.inflate(R.layout.fragment_sheet, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTextView = view.findViewById(R.id.id_title);
        layout = view.findViewById(R.id.id_layout);
        layout.setOnClickListener(this);
        initTimetableView(view);
        ActSimulateRequestData();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        这里不要解绑，解绑之后会空指针
//        unbinder.unbind();
    }

    /**
     * 2秒后刷新界面，模拟网络请求
     */
    private void ActSimulateRequestData() {
        alertDialog=new AlertDialog.Builder(getContext())
                .setMessage("请求网络中..")
                .setTitle("Tips").create();
//        alertDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0x123);
            }
        }).start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(alertDialog!=null) alertDialog.hide();
//            mySubjects = SubjectRepertory.loadDefaultSubjects();

            if(subject!=null){
                if(mySubjects!=null){mySubjects.add(subject);}
            }
            if(mySubjects!=null){
                mWeekView.source(mySubjects).showView();
                mTimetableView.source(mySubjects).showView();
            }
        }
    };

    /**
     * 初始化课程控件
     */
    private void initTimetableView(View view) {
        //获取控件
        mWeekView = view.findViewById(R.id.id_weekview);
        mTimetableView = view.findViewById(R.id.id_timetableView);
        moreButton = view.findViewById(R.id.id_more);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopmenu();
            }
        });

        //设置周次选择属性
        mWeekView.curWeek(1)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = mTimetableView.curWeek();
                        //更新切换后的日期，从当前周cur->切换的周week
                        mTimetableView.onDateBuildListener()
                                .onUpdateDate(cur, week);
                        mTimetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        onWeekLeftLayoutClicked();
                    }
                })
                .isShow(false)//设置隐藏，默认显示
                .showView();

        mTimetableView.curWeek(1);
        mTimetableView.curTerm("大三下学期");
//        mTimetableView.itemHeight(155);
        mTimetableView.callback(new ISchedule.OnItemClickListener() {
            @Override
            public void onItemClick(View v, List<Schedule> scheduleList) {
                display(scheduleList);
            }
        });
        mTimetableView.callback(new ISchedule.OnItemLongClickListener() {
            @Override
            public void onLongClick(View v, int day, int start) {
                Toast.makeText(getActivity(),
                        "长按:周" + day + ",第" + start + "节",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //旗标布局点击监听
        mTimetableView.callback(new ISchedule.OnFlaglayoutClickListener() {
            @Override
            public void onFlaglayoutClick(int day, int start) {
                mTimetableView.hideFlaglayout();
                addSubject();
                Toast.makeText(getContext(),
                        "点击了旗标:周" + (day + 1) + ",第" + start + "节"+"已添加课程",
                        Toast.LENGTH_SHORT).show();
            }
        });
        mTimetableView.callback(new ISchedule.OnWeekChangedListener() {
            @Override
            public void onWeekChanged(int curWeek) {
                titleTextView.setText("第" + curWeek + "周");
            }
        });
        mTimetableView.callback(new OnItemBuildAdapter() {
            @Override
            public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {
                super.onItemUpdate(layout, textView, countTextView, schedule, gd);
                if (schedule.getName().equals("【广告】")) {
                    layout.removeAllViews();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                    layout.addView(imageView);
                    String url = (String) schedule.getExtras().get(MySubject.EXTRAS_AD_URL);

                    Glide.with(getContext())
                            .load(url)
                            .into(imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "进入广告网页链接", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        mTimetableView.showView();
    }

    /**
     * 更新一下，防止因程序在后台时间过长（超过一天）而导致的日期或高亮不准确问题。
     */
    @Override
    public void onStart() {
        super.onStart();
        mTimetableView.onDateBuildListener()
                .onHighLight();
    }

    /**
     * 周次选择布局的左侧被点击时回调<br/>
     * 对话框修改当前周次
     */
    protected void onWeekLeftLayoutClicked() {
        final String items[] = new String[20];
        int itemCount = mWeekView.itemCount();
        for (int i = 0; i < itemCount; i++) {
            items[i] = "第" + (i + 1) + "周";
        }
        target = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("设置当前周");
        builder.setSingleChoiceItems(items, mTimetableView.curWeek() - 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        target = i;
                    }
                });
        builder.setPositiveButton("设置为当前周", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (target != -1) {
                    mWeekView.curWeek(target + 1).updateView();
                    mTimetableView.changeWeekForce(target + 1);
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }
    /**
     * 显示内容
     *
     * @param beans
     */
    protected void display(List<Schedule> beans) {
        Intent intent = new Intent(SYApplication.getContext(), ClickButtonActivity.class);
        intent.putExtra("resId",R.id.details);
        intent.putExtra("title","编辑课程");
        Bundle bundle = new Bundle();
        bundle.putSerializable("beans", (Serializable) beans);
        intent.putExtra("beans",bundle);
        mActivity.startActivity(intent);
        String str = "";
        for (Schedule bean : beans) {
            str += bean.getName() + ","+bean.getWeekList().toString()+","+bean.getStart()+","+bean.getStep()+"\n";
        }
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }
    /**
     * 隐藏周次选择，此时需要将课表的日期恢复到本周并将课表切换到当前周
     */
    public void hideWeekView(){
        mWeekView.isShow(false);
        titleTextView.setTextColor(getResources().getColor(R.color.app_course_textcolor_blue));
        int cur = mTimetableView.curWeek();
        mTimetableView.onDateBuildListener()
                .onUpdateDate(cur, cur);
        mTimetableView.changeWeekOnly(cur);
    }

    public void showWeekView(){
        mWeekView.isShow(true);
        titleTextView.setTextColor(getResources().getColor(R.color.app_red));
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            switch (requestCode) {
//                case REQUEST_CODE_FILE_CHOOSE:
//                    if (data == null) {
//                        return;
//                    }
//                    Uri uri = data.getData();
//                    String name = FileUtils.getNameFromUri(getActivity(), uri);
//                    if (!FileUtils.getFileExtension(name).equals("xls")) {
//                        DialogUtils.showTipDialog(getActivity(), "请选择后缀名为xls的Excel文件");
//                        return;
//                    }
//                    String path = getActivity().getExternalCacheDir().getAbsolutePath() + File.separator + name;
//                    if (TextUtils.isEmpty(path)) {
//                        Utils.showToast("获取文件路径失败");
//                        return;
//                    }
//                    try {
//                        if (!FileUtils.fileCopy(getActivity().getContentResolver().openInputStream(uri), path)) {
//                            return;
//                        }
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                        return;
//                    }
//
//                    mySubjects = ExcelUtils.handleExcel(path);
//
//                    //mMyDBHelper.insertItems(sCourseList);
//                    saveCurrentTimetable();
//                    if(mySubjects!=null){
//                        mWeekView.source(mySubjects).showView();
//                        mTimetableView.source(mySubjects).showView();
//                    }
////                    updateTimetable();
//                    //Log.d("path", path);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//    private void saveCurrentTimetable() {
//        new FileUtils<List<MySubject>>().saveToJson(getActivity(), mySubjects, FileUtils.TIMETABLE_FILE_NAME);
//    }

    /**
     * 显示弹出菜单
     */
    public void showPopmenu() {
        PopupMenu popup = new PopupMenu(getContext(), moreButton);
        popup.getMenuInflater().inflate(R.menu.popmenu_base_func, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.top1:
                        addSubject();
                        break;
//                    case R.id.top2:
//                        deleteSubject();
//                        break;
                    case R.id.top3:
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("application/*");
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        getActivity().startActivityForResult(intent, REQUEST_CODE_FILE_CHOOSE);
                        break;
                    case R.id.top4:
                        hideNonThisWeek();
                        break;
                    case R.id.top5:
                        showNonThisWeek();
                        break;
                    case R.id.top9:
                        showTime();
                        break;
                    case R.id.top10:
                        hideTime();
                        break;
                    case R.id.top11:
                        showWeekView();
                        break;
                    case R.id.top12:
                        hideWeekView();
                        break;
                    case R.id.top13:
                        changeEnglishLanguage();
                        break;
                    case R.id.top14:
                        changeChineseLanguage();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    /**
     * 删除课程
     * 内部使用集合维护课程数据，操作集合的方法来操作它即可
     * 最后更新一下视图（全局更新）
     */
//    protected void deleteSubject() {
//        int size = mTimetableView.dataSource().size();
//        int pos = (int) (Math.random() * size);
//        if (size > 0) {
//            mTimetableView.dataSource().remove(pos);
//            mTimetableView.updateView();
//        }
//    }

    /**
     * 添加课程
     * 内部使用集合维护课程数据，操作集合的方法来操作它即可
     * 最后更新一下视图（全局更新）
     */
    protected void addSubject() {
        StartUtils.startActivityById(mActivity,R.id.top1,"新增课程");
    }

    /**
     * 隐藏非本周课程
     * 修改了内容的显示，所以必须更新全部（性能不高）
     * 建议：在初始化时设置该属性
     * <p>
     * updateView()被调用后，会重新构建课程，课程会回到当前周
     */
    protected void hideNonThisWeek() {
        mTimetableView.isShowNotCurWeek(false).updateView();
    }

    /**
     * 显示非本周课程
     * 修改了内容的显示，所以必须更新全部（性能不高）
     * 建议：在初始化时设置该属性
     */
    protected void showNonThisWeek() {
        mTimetableView.isShowNotCurWeek(true).updateView();
    }

    /**
     * 设置侧边栏最大节次，只影响侧边栏的绘制，对课程内容无影响
     *
     * @param num
     */
    protected void setMaxItem(int num) {
        mTimetableView.maxSlideItem(num).updateSlideView();
    }

    /**
     * 显示时间
     * 设置侧边栏构建监听，TimeSlideAdapter是控件实现的可显示时间的侧边栏
     */
    protected void showTime() {
        //第一节课开始时间与结束时间
        String[] endtimes = new String[]{
                "8:00", "9:00", "10:10", "11:00",
                "15:00", "16:00", "17:00", "18:00",
                "19:30", "20:30", "21:30", "22:30"
        };
        String[] times = new String[]{
                "8:00", "9:00", "10:10", "11:00",
                "15:00", "16:00", "17:00", "18:00",
                "19:30", "20:30", "21:30", "22:30"
        };
        OnSlideBuildAdapter listener = (OnSlideBuildAdapter) mTimetableView.onSlideBuildListener();
        listener.setTimes(times,endtimes)
                .setTimeTextColor(Color.BLACK);
        mTimetableView.updateSlideView();
    }

    /**
     * 隐藏时间
     * 将侧边栏监听置Null后，会默认使用默认的构建方法，即不显示时间
     * 只修改了侧边栏的属性，所以只更新侧边栏即可（性能高），没有必要更新全部（性能低）
     */
    protected void hideTime() {
        mTimetableView.callback((ISchedule.OnSlideBuildListener) null);
        mTimetableView.updateSlideView();
    }

    /**
     * 切换为英文
     */
    public void changeEnglishLanguage(){
        mTimetableView.callback(new OnEnglishDateBuildAdapter())
                .callback(new OnEnglishItemBuildAdapter())
                .updateView();
    }

    /**
     * 切换为中文
     */
    public void changeChineseLanguage(){
        mTimetableView.callback((ISchedule.OnDateBuildListener) null)
                .callback((ISchedule.OnItemBuildListener) null)
                .updateView();
    }
}
