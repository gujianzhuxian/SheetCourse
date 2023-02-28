package com.sheetcourse.mobileterminal;

//import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.sheetcourse.mobileterminal.activity.BaseActivity;
import com.sheetcourse.mobileterminal.adapter.MainPagerAdapter;
import com.sheetcourse.mobileterminal.fragment.sheet.SheetFragment;
import com.sheetcourse.mobileterminal.model.MySubject;
import com.sheetcourse.mobileterminal.utils.DialogUtils;
import com.sheetcourse.mobileterminal.utils.ExcelUtils;
import com.sheetcourse.mobileterminal.utils.FileUtils;
import com.sheetcourse.mobileterminal.utils.IndirectClass;
import com.sheetcourse.mobileterminal.utils.PersonnalisationUtils;
import com.sheetcourse.mobileterminal.utils.Utils;
import com.sheetcourse.mobileterminal.widgets.NoScrollViewPager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    public static IndirectClass indirectClass;
    private Bundle bundle;

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @BindView(R.id.actionBar)
    RelativeLayout actionBarLayout;
    @BindView(R.id.bottom_plan)
    RadioButton bottomPlan;
    @BindView(R.id.bottom_sheet)
    RadioButton bottomSheet;
    @BindView(R.id.bottom_me)
    RadioButton bottomMe;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.home_title)
    TextView homeTitle;
    ImageView imageView;

    private MainPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indirectClass = new IndirectClass(this,MainActivity.this);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        MySubject subject=(MySubject)intent.getSerializableExtra("Mysubject");
        mySubjects= (List<MySubject>) intent.getSerializableExtra("listsubject");
        System.out.println("自身传给自身的mysubjects"+mySubjects);
        initView(subject);
    }
    private void initView(MySubject subject) {
        adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(5);
        viewpager.setCurrentItem(0);
        homeTitle.setText("今日计划");
        bottomPlan.setChecked(true);
        if(subject!=null){
            Bundle bundle = new Bundle();
//            System.out.println(bundle);
            bundle.putSerializable("mysubject",subject);
            setBundle(bundle);
            bottomSheet.setChecked(true);
            viewpager.setCurrentItem(1, false);
            homeTitle.setText("课程表");
            actionBarLayout.setVisibility(View.GONE);
        }
        if(mySubjects!=null){
            Bundle bundle = new Bundle();
            bundle.putSerializable("mysubjects", (Serializable) mySubjects);
            setBundle(bundle);
            bottomSheet.setChecked(true);
            viewpager.setCurrentItem(1, false);
            homeTitle.setText("课程表");
            actionBarLayout.setVisibility(View.GONE);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bottom_plan:
                        viewpager.setCurrentItem(0, false);
                        homeTitle.setText("今日计划");
                        actionBarLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bottom_sheet:
                        viewpager.setCurrentItem(1, false);
                        homeTitle.setText("课程表");
                        actionBarLayout.setVisibility(View.GONE);
                        break;
                    case R.id.bottom_me:
                        viewpager.setCurrentItem(2, false);
                        homeTitle.setText("个人中心");
                        actionBarLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }
    final String FRAGMENT_TAG = "fragment";

    /*在fragment的管理类中，我们要实现这部操作，而他的主要作用是，当D这个activity回传数据到
这里碎片管理器下面的fragnment中时，往往会经过这个管理器中的onActivityResult的方法。*/
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Fragment f=(SheetFragment)getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
//        if (f == null) {
//            f = new SheetFragment();
////            f.setTargetFragment(f, 0);
//            //重新添加fragment
//            getSupportFragmentManager().beginTransaction().add(f, FRAGMENT_TAG).commit();
//        }
//        /*然后在碎片中调用重写的onActivityResult方法*/
//        f.onActivityResult(requestCode, resultCode, data);
//    }
    private static final int REQUEST_CODE_FILE_CHOOSE = 2;//请求代码文件选择
    List<MySubject> mySubjects;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FILE_CHOOSE:
                    if (data == null) {
                        System.out.println(data);
                        return;
                    }
                    Uri uri = data.getData();
                    String name = FileUtils.getNameFromUri(this, uri);
                    if (!FileUtils.getFileExtension(name).equals("xls")) {
                        DialogUtils.showTipDialog(this, "请选择后缀名为xls的Excel文件");
                        return;
                    }
                    String path = getExternalCacheDir().getAbsolutePath() + File.separator + name;
                    if (TextUtils.isEmpty(path)) {
                        Utils.showToast("获取文件路径失败");
                        return;
                    }
                    try {
                        if (!FileUtils.fileCopy(getContentResolver().openInputStream(uri), path)) {
                            return;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }

                    mySubjects = ExcelUtils.handleExcel(path);

                    //mMyDBHelper.insertItems(sCourseList);
                    saveCurrentTimetable();
                    System.out.println(mySubjects);
                    finish();
                    Intent intent=new Intent(this,MainActivity.class);
                    intent.putExtra("listsubject", (Serializable) mySubjects);
                    startActivity(intent);

//                    updateTimetable();
                    //Log.d("path", path);
                    break;
                default:
                    break;
            }
        }
    }
    private void saveCurrentTimetable() {
        new FileUtils<List<MySubject>>().saveToJson(this, mySubjects, FileUtils.TIMETABLE_FILE_NAME);
    }
}