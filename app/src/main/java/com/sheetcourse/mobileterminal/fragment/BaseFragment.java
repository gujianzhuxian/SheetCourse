package com.sheetcourse.mobileterminal.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import com.sheetcourse.mobileterminal.utils.CommonUtils;
import com.sheetcourse.mobileterminal.widgets.ContentPage;

import rx.Subscriber;

import java.util.ArrayList;



public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public ContentPage contentPage;
    public ProgressDialog pdLoading;
    private ArrayList<Subscriber> subscribers;
    public Activity mActivity;
    //[(91条消息) 解决Fragment中调用getActivity()为null的多种方法_哈哈云的博客-CSDN博客_getactivity为null]
    // (https://blog.csdn.net/qq_31010739/article/details/83348085)
//    [(91条消息) Android-Fragment 中使用 getActivity()为null的原因---剖析源码_哑巴湖小水怪的博客-CSDN博客_fragment getactivity为空]
//    (https://blog.csdn.net/changhuzichangchang/article/details/107255134)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity)context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * 初始化pdLoading
         */
        pdLoading = new ProgressDialog(getActivity());
        pdLoading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdLoading.setMessage("请稍后");
        pdLoading.setCanceledOnTouchOutside(false);
        pdLoading.setCancelable(true);
        /**
         * 创建Subscriber容器
         */
        subscribers  = new ArrayList<>();
        if (contentPage == null) {
            contentPage = new ContentPage(getActivity()) {
                @Override
                protected Object loadData() {
                    return requestData();
                }
                @Override
                protected View createSuccessView() {
                    return getSuccessView();
                }
            };
        } else {
            CommonUtils.removeSelfFromParent(contentPage);
        }
        return contentPage;
    }

    /**
     * 返回据的fragment填充的具体View
     */
    protected abstract View getSuccessView();

    /**
     * 返回请求服务器的数据
     */
    protected abstract Object requestData();

    public void refreshPage(Object o) {
        contentPage.refreshPage(o);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(subscribers!=null){
            for(Subscriber subscriber:subscribers){
                if(!subscriber.isUnsubscribed()){
                    subscriber.unsubscribe();
                }
            }
        }
    }

    public <T> Subscriber<T> addSubscriber(Subscriber<T> subscriber) {
        subscribers.add(subscriber);
        return subscriber;
    }
}



