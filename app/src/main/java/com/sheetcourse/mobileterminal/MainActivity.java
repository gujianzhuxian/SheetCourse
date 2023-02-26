package com.sheetcourse.mobileterminal;

//import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
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
import com.sheetcourse.mobileterminal.utils.IndirectClass;
import com.sheetcourse.mobileterminal.utils.PersonnalisationUtils;
import com.sheetcourse.mobileterminal.widgets.NoScrollViewPager;

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
}