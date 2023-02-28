package com.sheetcourse.mobileterminal.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.sheetcourse.mobileterminal.MainActivity;
import com.sheetcourse.mobileterminal.R;
import com.sheetcourse.mobileterminal.activity.ClickButtonActivity;
import com.sheetcourse.mobileterminal.fragment.me.MeFragment;
import com.sheetcourse.mobileterminal.fragment.plan.PlanFragment;
import com.sheetcourse.mobileterminal.fragment.sheet.AddCourseFragment;
import com.sheetcourse.mobileterminal.fragment.sheet.CourseDetailsFragment;
import com.sheetcourse.mobileterminal.fragment.sheet.SheetFragment;
import com.sheetcourse.mobileterminal.utils.IndirectClass;

/**
 * fragment的工厂类
 * zs on 2015/11/3.
 */
public class FragmentFactory {



    /**
     * 根据资源id返回不同的fragment
     */
    public static Fragment createById(int resId) {
        Fragment fragment = null;
        switch (resId) {
            case R.id.top1:// 增加课程页面
                fragment = new AddCourseFragment();
                break;
            case R.id.details:
                fragment=new CourseDetailsFragment();
                IndirectClass indirect= ClickButtonActivity.indirectClass;
                Context context= (Context) indirect.getContxt();
                ClickButtonActivity activity = (ClickButtonActivity) indirect.getActivity();
                Bundle bundle=activity.getCourseDetailbundle();
                if(bundle!=null){fragment.setArguments(bundle);}
                break;
        }
        return fragment;
    }

    /**
     * main
     *
     * @param position
     * @return
     */
    public static Fragment createForMain(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:// 计划页
                fragment = new PlanFragment();
                break;
            case 1:// 课表页
                //[(91条消息) Android普通类调用活动中函数的方法_小超小调的博客-CSDN博客_调用mainactivity中的函数]
                // (https://blog.csdn.net/qq_34205684/article/details/110822168)
                fragment = new SheetFragment();
                System.out.println("进入课表");
                IndirectClass indirect= MainActivity.indirectClass;
                Context context= (Context) indirect.getContxt();
                MainActivity activity = (MainActivity) indirect.getActivity();
                Bundle bundle=activity.getBundle();
                if(bundle!=null){fragment.setArguments(bundle);}//clickButtonActivity返回到MainActivity所携带的bundle

                break;
            case 2:// 我的
                fragment = new MeFragment();
                break;
        }
        return fragment;
    }

    /**
     * 运营中心
     *
     * @param position
     * @return
     */
//    public static Fragment createForOperation(int position) {
//        Fragment fragment = null;
//        switch (position) {
//            case 0:// 异常开闸
//                fragment = new UnusualOpenFragment();
//                break;
//            case 1:// 出入记录
//                fragment = new OutInRecordFragment();
//                break;
//            case 2:// 在场车辆
//                fragment = new OnParkCarFragment();
//                break;
//            case 3:// 僵尸车辆
//                fragment = new CorpseCarFragment();
//                break;
//            case 4:// 缴费记录
//                fragment = new PayRecordFragment();
//                break;
//        }
//        return fragment;
//    }


    /**
     * 消费中心
     *
     * @param position
     * @return
     */
//    public static Fragment createForConsume(int position) {
//        Fragment fragment = null;
//        switch (position) {
//            case 0:// 会员订单
//                fragment = new VipOrderFragment();
//                break;
//            case 1:// 停车订单
//                fragment = new ParkingOrderFragment();
//                break;
//            case 2:// 商家订单
//                fragment = new MerchantOrderFragment();
//                break;
//            case 3:// 餐停定单
//                fragment = new EatOrderFragment();
//                break;
//        }
//        return fragment;
//    }
    /**
     * 管理中心
     *
     * @param position
     * @return
     */
//    public static Fragment createForManager(int position) {
//        Fragment fragment = null;
//        switch (position) {
//            case 0:// 会员管理
//                fragment = new VipManagerFragment();
//                break;
//            case 1:// 停车管理
//                fragment = new MerchantManagerFragment();
//                break;
//        }
//        return fragment;
//    }

    /**
     * 数据中心
     *
     * @param position
     * @return
     */
//    public static Fragment createForData(int position) {
//        Fragment fragment = null;
//        switch (position) {
//            case 0:// 收费统计
//                fragment = new ChargeStatisticsFragment();
//                break;
//            case 1:// 车次统计
//                fragment = new CarStatisticsFragment();
//                break;
//            case 2:// 收入分析
//                fragment = new IncomeAnalysisFragment();
//                break;
//            case 3:// 车次分析
//                fragment = new CarAnalysisFragment();
//                break;
//        }
//        return fragment;
//    }
}
