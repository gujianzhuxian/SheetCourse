package com.sheetcourse.mobileterminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.sheetcourse.mobileterminal.MainActivity;
import com.sheetcourse.mobileterminal.R;
import com.sheetcourse.mobileterminal.fragment.FragmentFactory;
import com.sheetcourse.mobileterminal.fragment.sheet.AddCourseFragment;
import com.sheetcourse.mobileterminal.utils.IndirectClass;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 所有能点击的按钮全部跳转到这个页面
 * Created by zs on 2015/11/3.
 */
public class ClickButtonActivity extends BaseActivity {

    FragmentManager fm;
    public Intent intent;
    public FragmentTransaction ft;
    @BindView(R.id.action_bar_back)
    public ImageView actionBarBack;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.iv_right)
    public ImageView ivRight;
    @BindView(R.id.rl_actionbar)
    public RelativeLayout rlActionbar;
    @BindView(R.id.fl_click_button)
    public FrameLayout flClickButton;
    @BindView(R.id.tv_right)
    public TextView tvRight;

    public int resId;
    public String id;
    private Bundle CourseDetailbundle;
    public static IndirectClass indirectClass;

    public Bundle getCourseDetailbundle() {
        return CourseDetailbundle;
    }

    public void setCourseDetailbundle(Bundle courseDetailbundle) {
        CourseDetailbundle = courseDetailbundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_button);
        ButterKnife.bind(this);
        indirectClass = new IndirectClass(this, ClickButtonActivity.this);
        // 获取传递过来的资源id值
        intent = getIntent();

        resId = intent.getIntExtra("resId", 0);
        if (intent.getExtras() != null) {
            resId = intent.getExtras().getInt("resId");
            tvTitle.setText(intent.getExtras().getString("title"));
            CourseDetailbundle=intent.getBundleExtra("beans");
            setCourseDetailbundle(CourseDetailbundle);
        }
        // 这里需要传递其他值可以自己定义
        id = intent.getStringExtra("id");
        /**
         * 根据传递过来的不同的资源id值设置不同的fragment
         */
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_click_button, FragmentFactory.createById(resId));
        ft.commit();

        actionBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickButtonActivity.this.finish();
            }
        });
    }
}
